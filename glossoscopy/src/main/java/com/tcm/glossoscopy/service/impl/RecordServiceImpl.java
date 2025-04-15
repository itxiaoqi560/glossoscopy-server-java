package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.po.Member;
import com.tcm.glossoscopy.entity.po.Record;
import com.tcm.glossoscopy.entity.po.RecordCrude;
import com.tcm.glossoscopy.entity.vo.RecordCrudeVO;
import com.tcm.glossoscopy.entity.vo.RecordDetailVO;
import com.tcm.glossoscopy.mapper.RecordMapper;
import com.tcm.glossoscopy.mapper.MemberMapper;
import com.tcm.glossoscopy.entity.result.PageResult;
import com.tcm.glossoscopy.service.RecordService;
import com.tcm.glossoscopy.utils.HealthStatusUtil;
import com.tcm.glossoscopy.utils.HttpClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    private RecordMapper recordMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private HealthStatusUtil healthStatusUtil;
    @Resource
    private HttpClientUtil httpClientUtil;
    @Resource
    private RedisCache redisCache;


    /**
     * 根据记录id集合批量删除成员诊断记录
     *
     * @param idListDTO
     */
    @Override
    public void batchDeleteRecord(IdListDTO idListDTO) {
        //删除成员诊断记录
        recordMapper.batchDeleteById(idListDTO.getIdList(), UserIdContext.getId());
    }

    /**
     * 根据条件分页查询成员所有历史诊断记录
     *
     * @param page
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param memberName
     * @return
     */
    @Override
    public PageResult pageQueryUserRecord(Integer page, Integer pageSize, LocalDate beginTime, LocalDate endTime, String memberName) {
        //分页查询符合条件的诊断记录
        PageHelper.startPage(page, pageSize);
        //修改分页查询结束日期往后推一天
        if(!Objects.isNull(endTime)) endTime=endTime.plusDays(1);
        //分页查询
        List<RecordCrude> recordCrudeList = recordMapper.pageQueryUserRecord(beginTime, endTime,memberName, UserIdContext.getId());
        //转换成分页集合
        Page<RecordCrude> recordCrudePage = (Page<RecordCrude>) recordCrudeList;
        //获取符合要求的诊断记录集合
        recordCrudeList = recordCrudePage.getResult();
        List<RecordCrudeVO> recordCrudeVOList = recordCrudeList.stream().map(recordCrude -> BeanUtil.copyProperties(recordCrude, RecordCrudeVO.class)).collect(Collectors.toList());
        return new PageResult(recordCrudePage.getTotal(), recordCrudeVOList);
    }

    /**
     * 根据记录id获取详细诊断记录
     *
     * @param id
     * @return
     */
    @Override
    public RecordDetailVO getRecordDetailInfo(Long id) {
        RecordCrude recordCrude = recordMapper.getRecordCrudeById(id, UserIdContext.getId());
        RecordDetailVO recordDetailVO = BeanUtil.copyProperties(recordCrude, RecordDetailVO.class,"healthStatus");
        recordDetailVO.setHealthStatus(healthStatusUtil.getHealthStatusById(Long.valueOf(recordCrude.getHealthStatus().getValue())));
        return recordDetailVO;
    }

    /**
     * 根据记录id获取粗略诊断记录
     *
     * @param id
     * @return
     */
    @Override
    public RecordCrudeVO getRecordCrudeInfo(Long id) {
        RecordCrude recordCrude = recordMapper.getRecordCrudeById(id, UserIdContext.getId());
        RecordCrudeVO recordCrudeVO = BeanUtil.copyProperties(recordCrude, RecordCrudeVO.class);
        return recordCrudeVO;
    }

    /**
     * 根据成员上传图片进行舌诊
     *
     * @param memberId
     * @param url
     * @return
     */
    @Transactional
    @Override
    @SentinelResource(value = "myResource")
    public RecordDetailVO diagnose(Long memberId, String url) {
        //查询用户成员信息
        Member member = memberMapper.getById(
                Member.builder()
                        .id(memberId)
                        .userId(UserIdContext.getId())
                        .build());
        //判断该成员是否属于该用户，若不属于，则返回null
        if (Objects.isNull(member)) {
            return null;
        }
        //发送http请求获取成员详细诊断记录
        Record record = httpClientUtil.getRecordBySendHttpRequest(url);
        //保存成员诊断记录信息
        record.setMemberId(memberId);
        record.setUserId(UserIdContext.getId());
        record.setDeleteFlag(false);
        recordMapper.addRecord(record);
        memberMapper.updateById(
                Member.builder()
                        .id(memberId)
                        .userId(UserIdContext.getId())
                        .healthStatus(record.getHealthStatus())
                        .build());
        redisCache.delete(RedisConstant.MEMBER_KEY+UserIdContext.getId());
        return getRecordDetailVO(record, member);
    }

    @Override
    public RecordCrudeVO getRandomRecord() {
        RecordCrude recordCrude = recordMapper.getRecordByDoctorId(UserIdContext.getId());
        RecordCrudeVO recordCrudeVO = BeanUtil.copyProperties(recordCrude, RecordCrudeVO.class);
        return recordCrudeVO;
    }

    /**
     * 将诊断记录信息和用户信息整合成为详细诊断记录信息
     *
     * @param record
     * @param member
     * @return
     */
    private RecordDetailVO getRecordDetailVO(Record record, Member member) {
        //获取详细诊断记录
        RecordDetailVO recordDetailVO = new RecordDetailVO();
        BeanUtil.copyProperties(record, recordDetailVO, "healthStatus");
        recordDetailVO.setMemberName(member.getMemberName());
        recordDetailVO.setSex(member.getSex());
        //设置成员诊断时的年龄
        recordDetailVO.setAge(record.getCreateTime().getYear() - member.getBirthday().getYear());
        //设置当前诊断记录对应体质的详细信息
        recordDetailVO.setHealthStatus(healthStatusUtil.getHealthStatusById(record.getHealthStatus().getValue().longValue()));
        return recordDetailVO;
    }
}
