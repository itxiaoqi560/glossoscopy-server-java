package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 根据成员id批量删除
     *
     * @param idList
     * @param userId
     */
    void batchDeleteById(List<Long> idList, Long userId);

    /**
     * 添加成员信息
     *
     * @param member
     */
    @Insert("insert into tb_member(user_id, member_name, avatar, sex, birthday, health_status,address, anamnesis, occupation, delete_flag,create_time, update_time) value(#{userId},#{memberName},#{avatar},#{sex},#{birthday},#{healthStatus},#{address},#{anamnesis},#{occupation},#{deleteFlag},#{createTime},#{updateTime})")
    void addMember(Member member);

    /**
     * 根据用户id获取成员信息
     *
     * @param userId
     */
    @Select("select * from tb_member where user_id = #{userId} and delete_flag=false order by create_time desc")
    List<Member> getByUserId(Long userId);

    /**
     * 根据成员id修改成员信息
     *
     * @param member
     * @return
     */
    void updateInfoById(Member member);

    /**
     * 根据成员id获取成员信息
     *
     * @param member
     * @return
     */
    @Select("select * from tb_member where id = #{id} and user_id=#{userId} and delete_flag=false")
    Member getById(Member member);

    /**
     * 根据用户id批量删除成员信息
     *
     * @param userId
     */
    @Update("update tb_member set delete_flag=true where user_id=#{userId}")
    void batchDeleteByUserId(Long userId);
}
