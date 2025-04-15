package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.ChatMessageVO;
import com.tcm.glossoscopy.service.ChatMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "聊天接口")
@RequestMapping("/api/chatMessage")
@Slf4j
public class ChatMessageController {

    @Resource
    private ChatMessageService chatMessageService;

    @GetMapping("/getChatMessage")
    @ApiOperation("获取聊天记录")
    @Loggable(value = "获取聊天记录")
    @PreAuthorize("hasAuthority('TCM:USER')")
    public Result getChatMessage(Long topicId) {
        log.info("获取聊天记录：{},{}", UserIdContext.getId(),topicId);
        List<ChatMessageVO> chatMessageVOList= chatMessageService.getChatMessage(topicId);
        return Result.success(chatMessageVOList);
    }


}