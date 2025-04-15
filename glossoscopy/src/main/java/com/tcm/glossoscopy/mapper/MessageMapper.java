package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.ChatMessage;
import com.tcm.glossoscopy.entity.po.Message;
import com.tcm.glossoscopy.entity.po.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Select("select * from tb_message where delete_flag=false order by create_time desc limit #{offset},10")
    List<Message> getByOffset(Integer offset);
}
