package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.ChatTopicDTO;
import com.tcm.glossoscopy.entity.po.ChatTopic;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.vo.ChatTopicVO;

import java.util.List;

public interface ChatTopicService extends IService<ChatTopic> {

    void deleteChatTopic(ChatTopicDTO chatTopicDTO);

    List<ChatTopicVO> getChatTopic();
}
