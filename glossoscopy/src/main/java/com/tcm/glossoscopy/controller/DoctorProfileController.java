package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.DoctorAdviceDTO;
import com.tcm.glossoscopy.entity.dto.DoctorProfileDTO;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.po.DoctorProfile;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.DoctorProfileVO;
import com.tcm.glossoscopy.service.DoctorProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "医生简历接口")
@RequestMapping("/api/doctorProfile")
@Slf4j
public class DoctorProfileController {
    @Resource
    private DoctorProfileService doctorProfileService;

    /**
     * 修改医生简历
     *
     * @param doctorProfileDTO
     * @return
     */
    @PutMapping("/updateDoctorProfile")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("修改医生简历")
    @Loggable(value = "修改医生简历")
    public Result updateDoctorProfile(@RequestBody DoctorProfileDTO doctorProfileDTO) {
        log.info("修改医生简历：{},{}", UserIdContext.getId(), doctorProfileDTO);
        doctorProfileService.updateDoctorProfile(doctorProfileDTO);
        return Result.success();
    }

    /**
     * 新增医生简历
     *
     * @param doctorProfileDTO
     * @return
     */
    @PostMapping("/addDoctorProfile")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("新增医生简历")
    @Loggable(value = "新增医生简历")
    public Result addDoctorProfile(@RequestBody DoctorProfileDTO doctorProfileDTO) {
        log.info("新增医生简历：{},{}", UserIdContext.getId(), doctorProfileDTO);
        doctorProfileService.addDoctorProfile(doctorProfileDTO);
        return Result.success();
    }

    /**
     * 查询医生简历
     *
     * @return
     */
    @GetMapping("/getDoctorProfile")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("查询医生简历")
    @Loggable(value = "查询医生简历")
    public Result getDoctorProfile() {
        log.info("查询医生简历：{}", UserIdContext.getId());
        DoctorProfileVO doctorProfileVO = doctorProfileService.getDoctorProfile();
        return Result.success(doctorProfileVO);
    }

}
