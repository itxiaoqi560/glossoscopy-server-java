package com.tcm.glossoscopy.entity.vo;

import com.alibaba.druid.pool.ha.selector.StickyRandomDataSourceSelector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusReportVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //体质类型，以逗号分隔，例如：平和体质，阴虚体质，阳虚体质
    private List<String> healthStatusList;
    //体质状态总量，以逗号分隔，例如：200，210，220
    private List<Long> totalHealthStatusList;
}