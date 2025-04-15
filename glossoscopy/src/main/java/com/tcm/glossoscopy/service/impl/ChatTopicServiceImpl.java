package com.tcm.glossoscopy.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.ChatTopicDTO;
import com.tcm.glossoscopy.entity.po.ChatTopic;
import com.tcm.glossoscopy.entity.vo.ChatTopicVO;
import com.tcm.glossoscopy.mapper.ChatTopicMapper;
import com.tcm.glossoscopy.service.ChatTopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatTopicServiceImpl extends ServiceImpl<ChatTopicMapper, ChatTopic> implements ChatTopicService {

    @Resource
    private ChatTopicMapper chatTopicMapper;


    @Override
    public void deleteChatTopic(ChatTopicDTO chatTopicDTO) {
        chatTopicMapper.deleteById(chatTopicDTO.getId(),UserIdContext.getId());
    }

    @Override
    public List<ChatTopicVO> getChatTopic() {
        List<ChatTopicVO> chatTopicVOList=chatTopicMapper.getVOByUserId(UserIdContext.getId());
        return chatTopicVOList;
    }
}