package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 为用户添加角色
     *
     * @param userRole
     */
    @Insert("insert into tb_user_role value(#{userId},#{roleId})")
    void addUserRole(UserRole userRole);

}