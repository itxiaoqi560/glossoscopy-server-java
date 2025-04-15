package com.tcm.glossoscopy.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SimpleMessageUtil {
    @Resource
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String destination,String jsonStr){
        messagingTemplate.convertAndSend("/topic/messages/"+destination,jsonStr);
    }
}
