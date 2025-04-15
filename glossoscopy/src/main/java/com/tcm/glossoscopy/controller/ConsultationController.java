package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.ConsultationDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.ConsultationVO;
import com.tcm.glossoscopy.entity.vo.DoctorProfileVO;
import com.tcm.glossoscopy.entity.vo.RecordCrudeVO;
import com.tcm.glossoscopy.service.ConsultationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "聊天主题接口")
@RequestMapping("/api/consultation")
@Slf4j
public class ConsultationController {
    @Resource
    private ConsultationService consultationService;

    /**
     * 医生坐诊
     *
     * @return
     */
    @PostMapping("/startConsultation")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("医生坐诊")
    @Loggable(value = "医生坐诊")
    public Result startConsultation() {
        log.info("医生坐诊：{}", UserIdContext.getId());
        String doctorUUID = consultationService.startConsultation();
        return Result.success(doctorUUID);
    }

    /**
     * 医生暂停坐诊
     *
     * @param consultationDTO
     * @return
     */
    @DeleteMapping("/exitConsultation")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("医生暂停坐诊")
    @Loggable(value = "医生暂停坐诊")
    public Result exitConsultation(@RequestBody ConsultationDTO consultationDTO) {
        log.info("医生暂停坐诊：{}", UserIdContext.getId());
        consultationService.exitConsultation(consultationDTO);
        return Result.success();
    }

    /**
     * 用户申请线上问诊
     *
     * @param consultationDTO
     * @return
     */
    @PostMapping("/applyConsultation")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("用户申请线上问诊")
    @Loggable(value = "用户申请线上问诊")
    public Result applyConsultation(@RequestBody ConsultationDTO consultationDTO) {
        log.info("用户申请线上问诊：{}", UserIdContext.getId());
        ConsultationVO consultationVO = consultationService.applyConsultation(consultationDTO);
        return Result.success(consultationVO);
    }

    /**
     * 结束线上问诊
     *
     * @param consultationDTO
     * @return
     */
    @DeleteMapping("/endConsultation")
    @PreAuthorize("hasAnyAuthority('TCM:USER','TCM:DOCTOR')")
    @ApiOperation("结束线上问诊")
    @Loggable(value = "结束线上问诊")
    public Result endConsultation(@RequestBody ConsultationDTO consultationDTO) {
        log.info("结束线上问诊：{}", UserIdContext.getId());
        consultationService.endConsultation(consultationDTO);
        return Result.success();
    }

    /**
     * 获取诊断记录信息
     *
     * @param receiverUUID
     * @return
     */
    @GetMapping("/getRecord")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("获取诊断记录信息")
    @Loggable(value = "获取诊断记录信息")
    public Result getRecord(String receiverUUID) {
        log.info("获取诊断记录信息：{}", UserIdContext.getId());
        RecordCrudeVO recordCrudeVO = consultationService.getRecord(receiverUUID);
        return Result.success(recordCrudeVO);
    }

    /**
     * 获取医生简历信息
     *
     * @param receiverUUID
     * @return
     */
    @GetMapping("/getDoctorProfile")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("获取医生简历信息")
    @Loggable(value = "获取医生简历信息")
    public Result getDoctorProfile(String receiverUUID) {
        log.info("获取医生简历信息：{}", UserIdContext.getId());
        DoctorProfileVO doctorProfileVO = consultationService.getDoctorProfile(receiverUUID);
        return Result.success(doctorProfileVO);
    }

    @PostMapping("/sendMessage")
    @ApiOperation("发送聊天消息")
    @Loggable(value = "发送聊天消息")
    @PreAuthorize("hasAnyAuthority('TCM:DOCTOR','TCM:USER')")
    public Result sendMessage(@RequestBody ConsultationDTO consultationDTO) {
        log.info("发送聊天消息：{},{}", UserIdContext.getId(), consultationDTO);
        consultationService.sendMessage(consultationDTO);
        return Result.success();
    }

    @PostMapping("/acceptConsultation")
    @ApiOperation("接受用户问诊")
    @Loggable(value = "接受用户问诊")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    public Result acceptConsultation(@RequestBody ConsultationDTO consultationDTO) {
        log.info("接受用户问诊：{},{}", UserIdContext.getId(), consultationDTO);
        consultationService.acceptConsultation(consultationDTO);
        return Result.success();
    }

    @PostMapping("/rejectConsultation")
    @ApiOperation("拒绝用户问诊")
    @Loggable(value = "拒绝用户问诊")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    public Result rejectConsultation(@RequestBody ConsultationDTO consultationDTO) {
        log.info("拒绝用户问诊：{},{}", UserIdContext.getId(), consultationDTO);
        consultationService.rejectConsultation(consultationDTO);
        return Result.success();
    }


}
