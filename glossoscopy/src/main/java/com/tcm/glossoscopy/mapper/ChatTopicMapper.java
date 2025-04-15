package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.ChatTopic;
import com.tcm.glossoscopy.entity.vo.ChatTopicVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatTopicMapper extends BaseMapper<ChatTopic> {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into tb_chat_topic(user_id, doctor_id, member_id,record_id, delete_flag, create_time) value (#{userId},#{doctorId},#{memberId},#{recordId},#{deleteFlag},#{createTime})")
    void addChatTopic(ChatTopic chatTopic);

    @Update("update tb_chat_topic set delete_flag=true where id = #{id} and user_id =#{userId}")
    void deleteById(Long id,Long userId);

    List<ChatTopicVO> getVOByUserId(Long userId);
}
