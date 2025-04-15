package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.DoctorAdviceDTO;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.po.DoctorAdvice;
import com.tcm.glossoscopy.entity.po.Member;
import com.tcm.glossoscopy.entity.result.PageResult;

import java.time.LocalDate;

public interface DoctorAdviceService extends IService<DoctorAdvice> {
    void addDoctorAdvice(DoctorAdviceDTO doctorAdviceDTO);

    PageResult pageQueryDoctorAdvice(Integer page, Integer pageSize, LocalDate beginTime, LocalDate endTime);

    void batchDeleteDoctorAdvice(IdListDTO idListDTO);

    void updateDoctorAdvice(DoctorAdviceDTO doctorAdviceDTO);
}
