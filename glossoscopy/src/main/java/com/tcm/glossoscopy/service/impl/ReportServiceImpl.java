package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.po.Member;
import com.tcm.glossoscopy.entity.po.Record;
import com.tcm.glossoscopy.entity.vo.HealthStatusReportVO;
import com.tcm.glossoscopy.entity.vo.RecordReportVO;
import com.tcm.glossoscopy.enums.HealthStatusEnum;
import com.tcm.glossoscopy.mapper.MemberMapper;
import com.tcm.glossoscopy.mapper.RecordMapper;
import com.tcm.glossoscopy.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private MemberMapper memberMapper;

    /**
     * 统计成员体质分布数据
     *
     * @return
     */
    @Override
    public HealthStatusReportVO getHealthStatusStatistics() {
        List<Member> memberList = memberMapper.getByUserId(UserIdContext.getId());
        if (CollUtil.isEmpty(memberList)) {
            return new HealthStatusReportVO(Collections.emptyList(), Collections.emptyList());
        }
        Map<HealthStatusEnum, Long> collect = memberList.stream()
                .collect(Collectors.groupingBy(
                        Member::getHealthStatus,
                        Collectors.counting()));
        List<String> healthStatusList = new ArrayList<>();
        List<Long> totalHealthStatusList = new ArrayList<>();
        collect.forEach(((healthStatusEnum, totalHealthStatus) -> {
            healthStatusList.add(healthStatusEnum.getDesc());
            totalHealthStatusList.add(totalHealthStatus);
        }));
        HealthStatusReportVO healthStatusReportVO = HealthStatusReportVO.builder()
                .healthStatusList(healthStatusList)
                .totalHealthStatusList(totalHealthStatusList)
                .build();
        return healthStatusReportVO;
    }

    /**
     * 统计近七天诊断记录分布数据
     *
     * @return
     */
    @Override
    public RecordReportVO getRecordStatistics() {
        LocalDate endTime = LocalDate.now().plusDays(1);
        LocalDate beginTime = endTime.minusDays(7);
        List<Record> recordList = recordMapper.getByTime(beginTime, endTime, UserIdContext.getId());
        Map<LocalDate, Long> collect = recordList.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getCreateTime().toLocalDate(),
                        Collectors.counting()));
        while (!beginTime.equals(endTime)) {
            collect.put(beginTime, collect.getOrDefault(beginTime, 0L));
            beginTime = beginTime.plusDays(1);
        }
        collect = collect.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        List<LocalDate> dateList = new ArrayList<>();
        List<Long> totalRecordList = new ArrayList<>();
        collect.forEach((date, totalRecord) -> {
            dateList.add(date);
            totalRecordList.add(totalRecord);
        });
        RecordReportVO recordReportVO = RecordReportVO.builder()
                .dateList(dateList)
                .totalRecordList(totalRecordList)
                .build();
        return recordReportVO;
    }

}
