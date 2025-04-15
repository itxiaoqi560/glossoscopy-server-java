package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.ChatTopicDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.ChatTopicVO;
import com.tcm.glossoscopy.service.ChatTopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Api(tags = "聊天主题接口")
@RequestMapping("/api/chatTopic")
@Slf4j
public class ChatTopicController {
    @Resource
    private ChatTopicService chatTopicService;

    @GetMapping("/getChatTopic")
    @ApiOperation("获取聊天主题")
    @Loggable(value = "获取聊天主题")
    @PreAuthorize("hasAuthority('TCM:USER')")
    public Result getChatTopic() {
        log.info("获取聊天主题：{}", UserIdContext.getId());
        List<ChatTopicVO> chatTopicVOList= chatTopicService.getChatTopic();
        return Result.success(chatTopicVOList);
    }

    @DeleteMapping("/deleteChatTopic")
    @ApiOperation("删除聊天主题")
    @Loggable(value = "删除聊天主题")
    @PreAuthorize("hasAuthority('TCM:USER')")
    public Result deleteChatTopic(@RequestBody ChatTopicDTO chatTopicDTO) {
        log.info("删除聊天主题：{},{}", UserIdContext.getId(),chatTopicDTO);
        chatTopicService.deleteChatTopic(chatTopicDTO);
        return Result.success();
    }

}
