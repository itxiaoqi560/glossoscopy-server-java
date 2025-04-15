package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.DoctorAdviceDTO;
import com.tcm.glossoscopy.entity.dto.DoctorProfileDTO;
import com.tcm.glossoscopy.entity.po.DoctorAdvice;
import com.tcm.glossoscopy.entity.po.DoctorProfile;
import com.tcm.glossoscopy.entity.vo.DoctorProfileVO;
import com.tcm.glossoscopy.mapper.DoctorProfileMapper;
import com.tcm.glossoscopy.service.DoctorProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class DoctorProfileServiceImpl extends ServiceImpl<DoctorProfileMapper, DoctorProfile> implements DoctorProfileService {
    @Resource
    private DoctorProfileMapper doctorProfileMapper;
    @Override
    public void addDoctorProfile(DoctorProfileDTO doctorProfileDTO) {
        DoctorProfile doctorProfile = BeanUtil.copyProperties(doctorProfileDTO, DoctorProfile.class);
        doctorProfile.setDoctorId(UserIdContext.getId());
        LocalDateTime nowTime=LocalDateTime.now();
        doctorProfile.setUpdateTime(nowTime);
        doctorProfile.setCreateTime(nowTime);
        doctorProfileMapper.addDoctorProfile(doctorProfile);
    }

    @Override
    public DoctorProfileVO getDoctorProfile() {
        DoctorProfile doctorProfile = doctorProfileMapper.getByDoctorId(UserIdContext.getId());
        DoctorProfileVO doctorProfileVO = BeanUtil.copyProperties(doctorProfile, DoctorProfileVO.class);
        return doctorProfileVO;
    }

    @Override
    public void updateDoctorProfile(DoctorProfileDTO doctorProfileDTO) {
        DoctorProfile doctorProfile = BeanUtil.copyProperties(doctorProfileDTO, DoctorProfile.class);
        doctorProfile.setDoctorId(UserIdContext.getId());
        doctorProfile.setUpdateTime(LocalDateTime.now());
        doctorProfileMapper.updateInfoByDoctorId(doctorProfile);
    }
}
