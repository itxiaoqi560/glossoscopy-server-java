package com.tcm.glossoscopy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordReportVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //日期，以逗号分隔，例如：2022-10-01，2022-10-02，2022-10-03
    private List<LocalDate> dateList;
    //诊断记录总量，以逗号分隔，例如：200，210，220
    private List<Long> totalRecordList;
}