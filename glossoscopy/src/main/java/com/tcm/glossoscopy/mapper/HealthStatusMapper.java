package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.HealthStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HealthStatusMapper extends BaseMapper<HealthStatus> {
    /**
     * 查询所有体质对应的结果信息
     * @return
     */
    @Select("select * from tb_health_status")
    List<HealthStatus> getHealthStatus();
}
