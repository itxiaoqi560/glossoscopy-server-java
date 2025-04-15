package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.DoctorAdviceDTO;
import com.tcm.glossoscopy.entity.dto.DoctorProfileDTO;
import com.tcm.glossoscopy.entity.po.DoctorProfile;
import com.tcm.glossoscopy.entity.po.Member;
import com.tcm.glossoscopy.entity.vo.DoctorProfileVO;

public interface DoctorProfileService extends IService<DoctorProfile> {
    void addDoctorProfile(DoctorProfileDTO doctorProfileDTO);

    DoctorProfileVO getDoctorProfile();

    void updateDoctorProfile(DoctorProfileDTO doctorProfileDTO);
}
