package com.tcm.glossoscopy.controller;


import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.MemberDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.MemberVO;
import com.tcm.glossoscopy.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "成员接口")
@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    /**
     * 添加成员信息
     *
     * @param memberDTO
     * @return
     */
    @PostMapping("/addMemberInfo")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @ApiOperation("添加成员信息")
    @Loggable(value = "添加成员信息")
    public Result addMemberInfo(@RequestBody MemberDTO memberDTO) {
        log.info("添加成员信息：{},{}", UserIdContext.getId(), memberDTO);
        memberService.addMemberInfo(memberDTO);
        return Result.success();
    }


    /**
     * 根据用户id获取成员信息
     *
     * @return
     */
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @GetMapping("/getUserMemberInfo")
    @ApiOperation("获取用户成员信息")
    @Loggable(value = "获取用户成员信息")
    public Result getUserMemberInfo() {
        log.info("获取用户成员信息：{}", UserIdContext.getId());
        List<MemberVO> memberVOList = memberService.getUserMemberInfo();
        return Result.success(memberVOList);
    }

    /**
     * 根据成员id获取成员信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getMemberInfo")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @ApiOperation("获取特定成员信息")
    @Loggable(value = "获取特定成员信息")
    public Result getMemberInfo(Long id) {
        log.info("获取特定成员信息：{},{}", UserIdContext.getId(),id);
        MemberVO memberVOList = memberService.getMemberInfo(id);
        return Result.success(memberVOList);
    }

    /**
     * 根据成员id批量删除成员信息
     *
     * @param idListDTO
     * @return
     */
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @DeleteMapping("/batchDeleteMemberInfo")
    @ApiOperation("批量删除成员信息")
    @Loggable(value = "批量删除成员信息")
    public Result batchDeleteMemberInfo(@RequestBody IdListDTO idListDTO) {
        log.info("批量删除成员信息：{},{}", UserIdContext.getId(), idListDTO);
        memberService.batchDeleteMemberInfo(idListDTO);
        return Result.success();
    }

    /**
     * 根据成员id修改成员信息
     *
     * @param memberDTO
     * @return
     */
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @PutMapping("/updateMemberInfo")
    @ApiOperation("修改成员信息")
    @Loggable(value = "修改成员信息")
    public Result updateMemberInfo(@RequestBody MemberDTO memberDTO) {
        log.info("修改成员信息：{},{}", UserIdContext.getId(), memberDTO);
        memberService.updateMemberInfo(memberDTO);
        return Result.success();
    }

}
