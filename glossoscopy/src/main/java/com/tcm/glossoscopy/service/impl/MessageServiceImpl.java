package com.tcm.glossoscopy.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.entity.po.Message;
import com.tcm.glossoscopy.entity.vo.MessageVO;
import com.tcm.glossoscopy.mapper.MessageMapper;
import com.tcm.glossoscopy.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Resource
    private MessageMapper messageMapper;


    @Override
    public List<MessageVO> getMessage(Integer offset) {
        List<Message> messageList = messageMapper.getByOffset(offset);
        List<MessageVO> messageVOList = messageList.stream()
                .map(message -> BeanUtil.copyProperties(message, MessageVO.class))
                .collect(Collectors.toList());
        return messageVOList;
    }
}