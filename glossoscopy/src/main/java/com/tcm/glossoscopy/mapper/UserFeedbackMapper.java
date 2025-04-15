package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.UserFeedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserFeedbackMapper extends BaseMapper<UserFeedback> {
    @Insert("insert into tb_user_feedback(user_id, type, content, phone_number, delete_flag,create_time) value (#{userId},#{type},#{content},#{phoneNumber},#{deleteFlag},#{createTime})")
    void addUserFeedBack(UserFeedback userFeedback);


    void batchDeleteById(List<Long> idList,Long userId);

    List<UserFeedback> pageQueryUserFeedback(LocalDate beginTime, LocalDate endTime,Long userId);
}
