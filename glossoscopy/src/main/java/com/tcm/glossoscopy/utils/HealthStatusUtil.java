package com.tcm.glossoscopy.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.tcm.glossoscopy.entity.po.HealthStatus;
import com.tcm.glossoscopy.enums.HealthStatusEnum;
import com.tcm.glossoscopy.mapper.HealthStatusMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HealthStatusUtil {
    private Map<Long, HealthStatus> mp=new ConcurrentHashMap<>();

    @Resource
    private HealthStatusMapper healthStatusMapper;

    public HealthStatus getHealthStatusById(Long id){
        if(CollUtil.isEmpty(mp)){
            List<HealthStatus> healthStatusList = healthStatusMapper.getHealthStatus();
            for(HealthStatus healthStatus:healthStatusList){
                mp.put(healthStatus.getId(),healthStatus);
            }
        }
        return mp.get(id);
    }
}
