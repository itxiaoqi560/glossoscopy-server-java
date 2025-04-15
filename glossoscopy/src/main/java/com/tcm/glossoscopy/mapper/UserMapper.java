package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 添加用户信息
     *
     * @param user
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into tb_user(username, password, avatar, account, phone_number,status, delete_flag,create_time,update_time) value(#{username},#{password},#{avatar},#{account},#{phoneNumber},#{status},#{deleteFlag},#{createTime},#{updateTime}) ")
    void addUser(User user);

    /**
     * 根据用户id删除用户信息
     *
     * @param id
     */
    @Update("update tb_user set delete_flag=true where id = #{id}")
    void deleteById(Long id);

    /**
     * 查询用户信息
     *
     * @param user
     */
    User getUser(User user);

    /**
     * 根据用户id更新用户信息
     *
     * @param user
     */
    void updateInfoById(User user);

}
