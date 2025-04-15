package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonObject;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.bo.LoginUser;
import com.tcm.glossoscopy.entity.dto.ConsultationDTO;
import com.tcm.glossoscopy.entity.po.ChatMessage;
import com.tcm.glossoscopy.entity.po.ChatTopic;
import com.tcm.glossoscopy.entity.po.DoctorProfile;
import com.tcm.glossoscopy.entity.po.RecordCrude;
import com.tcm.glossoscopy.entity.vo.ChatMessageVO;
import com.tcm.glossoscopy.entity.vo.ConsultationVO;
import com.tcm.glossoscopy.entity.vo.DoctorProfileVO;
import com.tcm.glossoscopy.entity.vo.RecordCrudeVO;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.mapper.ChatMessageMapper;
import com.tcm.glossoscopy.mapper.ChatTopicMapper;
import com.tcm.glossoscopy.mapper.DoctorProfileMapper;
import com.tcm.glossoscopy.mapper.RecordMapper;
import com.tcm.glossoscopy.service.ConsultationService;
import com.tcm.glossoscopy.utils.SimpleMessageUtil;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ConsultationServiceImpl extends ServiceImpl<ChatTopicMapper, ChatTopic> implements ConsultationService {
    @Resource
    private ChatTopicMapper chatTopicMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private SimpleMessageUtil simpleMessageUtil;
    @Resource
    private ChatMessageMapper chatMessageMapper;
    @Resource
    private DoctorProfileMapper doctorProfileMapper;
    private final Map<String, Long> doctorConnection = new ConcurrentHashMap<>();

    private static final DefaultRedisScript<Long> script;

    static {
        script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setLocation(new ClassPathResource("lua/chat.lua"));
    }

    @Override
    public String startConsultation() {
        String uuid = UUID.randomUUID().toString();
        redisCache.save(RedisConstant.CHAT_DOCTOR_KEY + uuid,
                UserIdContext.getId(),
                RedisConstant.CHAT_EXPIRE,
                TimeUnit.DAYS);
        doctorConnection.put(uuid, UserIdContext.getId());
        DoctorProfile doctorProfile = doctorProfileMapper.getByDoctorId(UserIdContext.getId());
        DoctorProfileVO doctorProfileVO = BeanUtil.copyProperties(doctorProfile, DoctorProfileVO.class);
        redisCache.save(RedisConstant.CHAT_PROFILE_KEY + uuid,
                doctorProfileVO,
                RedisConstant.CHAT_EXPIRE,
                TimeUnit.DAYS);
        return uuid;
    }

    @Override
    public void exitConsultation(ConsultationDTO consultationDTO) {
        if(doctorConnection.getOrDefault(consultationDTO.getSenderUUID(),-1L).equals(UserIdContext.getId())){
            doctorConnection.remove(consultationDTO.getSenderUUID());
            redisCache.delete(RedisConstant.CHAT_DOCTOR_KEY + consultationDTO.getSenderUUID());
            redisCache.delete(RedisConstant.CHAT_PROFILE_KEY + consultationDTO.getSenderUUID());
        }
    }

    @Override
    public ConsultationVO applyConsultation(ConsultationDTO consultationDTO) {
        Long userId = UserIdContext.getId();
        Long memberId = consultationDTO.getMemberId();
        RecordCrude recordCrude = recordMapper.getOneRecordCrudeByMemberId(memberId,
                userId,
                LocalDateTime.now().minusDays(7));
        if (Objects.isNull(recordCrude)) {
            throw new BusinessException(ExceptionEnum.MEMBER_HAS_NO_RECORDS_IN_PAST_SEVEN_DAY);
        }
        String senderUUID = UUID.randomUUID().toString();
        String receiverUUID = getReceiverUUID(senderUUID);
        if (!Strings.hasText(receiverUUID)) {
            throw new BusinessException(ExceptionEnum.NO_DOCTORS_AVAILABLE_FOR_CONSULTATION);
        }
        ChatTopic chatTopic = ChatTopic.builder()
                .memberId(memberId)
                .recordId(recordCrude.getId())
                .userId(userId)
                .deleteFlag(false)
                .createTime(LocalDateTime.now())
                .doctorId(redisCache.get(RedisConstant.CHAT_DOCTOR_KEY + receiverUUID, Long.TYPE))
                .build();
        chatTopicMapper.addChatTopic(chatTopic);
        redisCache.save(RedisConstant.CHAT_USER_KEY + senderUUID,
                userId,
                RedisConstant.CHAT_EXPIRE,
                TimeUnit.DAYS);
        RecordCrudeVO recordCrudeVO = BeanUtil.copyProperties(recordCrude, RecordCrudeVO.class);
        redisCache.save(RedisConstant.CHAT_RECORD_KEY + senderUUID,
                recordCrudeVO,
                RedisConstant.CHAT_EXPIRE,
                TimeUnit.DAYS);
        redisCache.save(RedisConstant.CHAT_TOPIC_KEY + senderUUID,
                chatTopic.getId(),
                RedisConstant.CHAT_EXPIRE,
                TimeUnit.DAYS);
        ConsultationVO consultationVO = ConsultationVO.builder()
                .receiverUUId(receiverUUID)
                .senderUUID(senderUUID)
                .build();
        return consultationVO;
    }

    @Override
    public void endConsultation(ConsultationDTO consultationDTO) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String senderUUID = consultationDTO.getSenderUUID();
        String receiverUUID = consultationDTO.getReceiverUUID();
        if (loginUser.getUserRole().equals("USER")) {
            redisCache.delete(RedisConstant.CHAT_USER_KEY + senderUUID);
            redisCache.delete(RedisConstant.CHAT_TOPIC_KEY + senderUUID);
            redisCache.inc(RedisConstant.CHAT_CONSULTATION_KEY + receiverUUID, -1L);
        } else {
            redisCache.delete(RedisConstant.CHAT_USER_KEY + receiverUUID);
            redisCache.delete(RedisConstant.CHAT_TOPIC_KEY + receiverUUID);
            redisCache.inc(RedisConstant.CHAT_CONSULTATION_KEY + senderUUID, -1L);
        }
        simpleMessageUtil.sendMessage(senderUUID + receiverUUID, "");
        simpleMessageUtil.sendMessage(receiverUUID + senderUUID, "");
    }

    @Override
    public RecordCrudeVO getRecord(String receiverUUID) {
        RecordCrudeVO recordCrudeVO = redisCache.get(RedisConstant.CHAT_RECORD_KEY + receiverUUID, RecordCrudeVO.class);
        redisCache.delete(RedisConstant.CHAT_RECORD_KEY + receiverUUID);
        return recordCrudeVO;
    }

    @Override
    public DoctorProfileVO getDoctorProfile(String receiverUUID) {
        DoctorProfileVO doctorProfileVO = redisCache.get(RedisConstant.CHAT_PROFILE_KEY + receiverUUID, DoctorProfileVO.class);
        return doctorProfileVO;
    }

    @Override
    public void sendMessage(ConsultationDTO consultationDTO) {
        String senderUUID = consultationDTO.getSenderUUID();
        String receiverUUID = consultationDTO.getReceiverUUID();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = loginUser.getUserRole();
        Long topicId;
        if (role.equals("USER")) {
            topicId = redisCache.get(RedisConstant.CHAT_TOPIC_KEY + senderUUID, Long.class);
        } else {
            topicId = redisCache.get(RedisConstant.CHAT_TOPIC_KEY + receiverUUID, Long.class);
        }
        ChatMessage chatMessage = ChatMessage.builder()
                .content(consultationDTO.getContent())
                .topicId(topicId)
                .createTime(LocalDateTime.now())
                .userId(UserIdContext.getId())
                .build();
        chatMessageMapper.addChatMessage(chatMessage);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",chatMessage.getContent());
        jsonObject.put("flag",false);
        jsonObject.put("createTime",chatMessage.getCreateTime());
        simpleMessageUtil.sendMessage(receiverUUID + senderUUID, jsonObject.toJSONString());
        jsonObject.put("flag",true);
        simpleMessageUtil.sendMessage(senderUUID + receiverUUID, jsonObject.toJSONString());
    }

    @Override
    public void acceptConsultation(ConsultationDTO consultationDTO) {
        redisCache.save(RedisConstant.CHAT_ACCEPT_KEY + consultationDTO.getReceiverUUID() + consultationDTO.getSenderUUID(),
                "1",
                RedisConstant.CHAT_ACCEPT_EXPIRE,
                TimeUnit.MINUTES);

    }

    @Override
    public void rejectConsultation(ConsultationDTO consultationDTO) {
        redisCache.save(RedisConstant.CHAT_ACCEPT_KEY + consultationDTO.getReceiverUUID() + consultationDTO.getSenderUUID(),
                "0",
                RedisConstant.CHAT_ACCEPT_EXPIRE,
                TimeUnit.MINUTES);
    }


    private String getReceiverUUID(String senderUUID) {
        Set<String> doctorUUIDSet = doctorConnection.keySet();
        int doctorCount=0;
        for (String receiverUUID : doctorUUIDSet) {
            if(doctorCount>=5){
                break;
            }
            Long result = redisCache.execute(script, List.of(RedisConstant.CHAT_CONSULTATION_KEY + receiverUUID));
            if (Objects.isNull(result)) {
                continue;
            }
            ++doctorCount;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("senderUUID",senderUUID);
            String jsonStr = jsonObject.toJSONString();
            simpleMessageUtil.sendMessage(receiverUUID, jsonStr);
            try {
                int count=0;
                while(count<=15){
                    Thread.sleep(1000L);
                    ++count;
                    String str = redisCache.get(RedisConstant.CHAT_ACCEPT_KEY + senderUUID + receiverUUID, String.class);
                    if (Strings.hasText(str)) {
                        if(str.equals("1")){
                            return receiverUUID;
                        }
                        break;
                    }
                }
                redisCache.inc(RedisConstant.CHAT_CONSULTATION_KEY + receiverUUID, -1L);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        return "";
    }
}