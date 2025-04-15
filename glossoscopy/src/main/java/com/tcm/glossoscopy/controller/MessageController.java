package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.MemberDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.MessageVO;
import com.tcm.glossoscopy.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "消息接口")
@RequestMapping("/api/message")
@Slf4j
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 用户获取消息
     *
     * @param offset
     * @return
     */
    @GetMapping("/getMessage")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("用户获取消息")
    @Loggable(value = "用户获取消息")
    public Result getMessage(Integer offset) {
        log.info("用户获取消息：{},{}", UserIdContext.getId(), offset);
        List<MessageVO> messageVOList=messageService.getMessage(offset);
        return Result.success(messageVOList);
    }
}
