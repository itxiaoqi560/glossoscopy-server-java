package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.DoctorAdviceDTO;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.po.DoctorAdvice;
import com.tcm.glossoscopy.entity.po.DoctorAdviceDetail;
import com.tcm.glossoscopy.entity.po.UserFeedback;
import com.tcm.glossoscopy.entity.result.PageResult;
import com.tcm.glossoscopy.entity.vo.DoctorAdviceDetailVO;
import com.tcm.glossoscopy.mapper.DoctorAdviceMapper;
import com.tcm.glossoscopy.service.DoctorAdviceService;
import com.tcm.glossoscopy.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorAdviceServiceImpl extends ServiceImpl<DoctorAdviceMapper, DoctorAdvice> implements DoctorAdviceService {
    @Resource
    private DoctorAdviceMapper doctorAdviceMapper;
    @Override
    public void addDoctorAdvice(DoctorAdviceDTO doctorAdviceDTO) {
        DoctorAdvice doctorAdvice = BeanUtil.copyProperties(doctorAdviceDTO, DoctorAdvice.class);
        doctorAdvice.setDoctorId(UserIdContext.getId());
        doctorAdvice.setDeleteFlag(false);
        LocalDateTime nowTime = LocalDateTime.now();
        doctorAdvice.setCreateTime(nowTime);
        doctorAdvice.setUpdateTime(nowTime);
        doctorAdviceMapper.addDoctorAdvice(doctorAdvice);

    }

    @Override
    public PageResult pageQueryDoctorAdvice(Integer page, Integer pageSize, LocalDate beginTime, LocalDate endTime) {
        PageHelper.startPage(page,pageSize);
        endTime=endTime.plusDays(1);
        List<DoctorAdviceDetail> doctorAdviceDetailList=doctorAdviceMapper.pageQueryDoctorAdvice(beginTime,endTime,UserIdContext.getId());
        Page<DoctorAdviceDetail> doctorAdviceDetailPage=(Page<DoctorAdviceDetail>) doctorAdviceDetailList;
        doctorAdviceDetailList = doctorAdviceDetailPage.getResult();
        List<DoctorAdviceDetailVO> doctorAdviceDetailVOList = doctorAdviceDetailList.stream().map(doctorAdviceDetail -> BeanUtil.copyProperties(doctorAdviceDetail, DoctorAdviceDetailVO.class)).collect(Collectors.toList());
        return new PageResult(doctorAdviceDetailPage.getTotal(),doctorAdviceDetailVOList);
    }

    @Override
    public void batchDeleteDoctorAdvice(IdListDTO idListDTO) {
        doctorAdviceMapper.batchDeleteDoctorAdvice(idListDTO.getIdList(),UserIdContext.getId());
    }

    @Override
    public void updateDoctorAdvice(DoctorAdviceDTO doctorAdviceDTO) {
        DoctorAdvice doctorAdvice = BeanUtil.copyProperties(doctorAdviceDTO, DoctorAdvice.class);
        doctorAdvice.setUpdateTime(LocalDateTime.now());
        doctorAdvice.setDoctorId(UserIdContext.getId());
        doctorAdviceMapper.updateInfoById(doctorAdvice);
    }

}
