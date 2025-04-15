package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.po.ChatMessage;
import com.tcm.glossoscopy.entity.vo.ChatMessageVO;

import java.util.List;

public interface ChatMessageService extends IService<ChatMessage> {

    List<ChatMessageVO> getChatMessage(Long topicId);
}
