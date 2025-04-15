package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.UserReview;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserReviewMapper extends BaseMapper<UserReview> {
    @Insert("insert into tb_user_review(user_id, doctor_id, rating, comment, create_time) value (#{userId},#{doctorId},#{rating},#{comment},#{createTime})")
    void addUserReview(UserReview userReview);
}
