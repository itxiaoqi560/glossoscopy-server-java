package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id获取权限信息
     *
     * @param userId
     * @return
     */
    List<String> getPermissionByUserId(Long userId);

}