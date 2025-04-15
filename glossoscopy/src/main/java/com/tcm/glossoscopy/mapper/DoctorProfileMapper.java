package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.DoctorAdvice;
import com.tcm.glossoscopy.entity.po.DoctorProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface DoctorProfileMapper extends BaseMapper<DoctorProfile> {
    @Insert("insert into tb_doctor_profile(doctor_id, title, introduction, specialty, hospital, work_experience, education, update_time, create_time)  value (#{doctorId},#{title},#{introduction},#{specialty},#{hospital},#{workExperience},#{education},#{updateTime},#{createTime})")
    void addDoctorProfile(DoctorProfile doctorProfile);

    @Select("select * from tb_doctor_profile where doctor_id = #{doctorId}")
    DoctorProfile getByDoctorId(Long doctorId);

    void updateInfoByDoctorId(DoctorProfile doctorProfile);
}
