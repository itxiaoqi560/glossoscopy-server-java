package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.DoctorAdvice;
import com.tcm.glossoscopy.entity.po.DoctorAdviceDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DoctorAdviceMapper extends BaseMapper<DoctorAdvice> {
    @Insert("insert into tb_doctor_advice(doctor_id, record_id, advice, delete_flag,update_time,create_time) value(#{doctorId},#{recordId},#{advice},#{deleteFlag},#{updateTime},#{createTime})")
    void addDoctorAdvice(DoctorAdvice doctorAdvice);

    List<DoctorAdviceDetail> pageQueryDoctorAdvice(LocalDate beginTime, LocalDate endTime, Long doctorId);

    void batchDeleteDoctorAdvice(List<Long> idList,Long doctorId);

    @Update("update tb_doctor_advice set advice = #{advice},update_time=#{updateTime} where doctor_id = #{doctorId} and id =#{id}")
    void updateInfoById(DoctorAdvice doctorAdvice);
}
