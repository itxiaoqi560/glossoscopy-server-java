package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.po.Message;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.vo.ChatMessageVO;
import com.tcm.glossoscopy.entity.vo.MessageVO;

import java.util.List;

public interface MessageService extends IService<Message> {

    List<MessageVO> getMessage(Integer offset);
}
