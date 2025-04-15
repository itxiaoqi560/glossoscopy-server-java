package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.ChatMessage;
import com.tcm.glossoscopy.entity.po.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
    @Insert("insert into tb_chat_message(topic_id, user_id, content, create_time) value (#{topicId},#{userId},#{content},#{createTime})")
    void addChatMessage(ChatMessage chatMessage);

    List<ChatMessage> getByTopicId(Long topicId, Long userId);
}
