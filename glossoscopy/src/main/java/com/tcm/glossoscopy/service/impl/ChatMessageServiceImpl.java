package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.po.ChatMessage;
import com.tcm.glossoscopy.entity.vo.ChatMessageVO;
import com.tcm.glossoscopy.mapper.ChatMessageMapper;
import com.tcm.glossoscopy.service.ChatMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

    @Resource
    private ChatMessageMapper chatMessageMapper;


    @Override
    public List<ChatMessageVO> getChatMessage(Long topicId) {
        Long userId= UserIdContext.getId();
        List<ChatMessage> chatMessageList = chatMessageMapper.getByTopicId(topicId,userId);
        List<ChatMessageVO> chatMessageVOList = chatMessageList.stream().map(chatMessage -> {
            ChatMessageVO chatMessageVO = BeanUtil.copyProperties(chatMessage, ChatMessageVO.class);
            chatMessageVO.setFlag(userId.equals(chatMessage.getUserId()));
            return chatMessageVO;
        }).collect(Collectors.toList());
        return chatMessageVOList;
    }
}