package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.Record;
import com.tcm.glossoscopy.entity.po.RecordCrude;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 插入诊断记录
     *
     * @param record
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into tb_record(user_id,member_id,image, tooth_mark, tongue_thickness, tongue_size, coating_color, health_status,delete_flag, create_time) value(#{userId},#{memberId},#{image},#{toothMark},#{tongueThickness},#{tongueSize},#{coatingColor},#{healthStatus},#{deleteFlag},#{createTime})")
    void addRecord(Record record);

    /**
     * 根据成员id删除诊断记录
     *
     * @param memberIdList
     * @param userId
     */
    void batchDeleteByMemberId(List<Long> memberIdList, Long userId);

    /**
     * 根据记录id批量删除诊断记录
     *
     * @param idList
     * @param userId
     */
    void batchDeleteById(List<Long> idList, Long userId);

    /**
     * 根据成员id获取历史诊断记录
     *
     * @param beginTime
     * @param endTime
     * @param memberName
     * @param userId
     * @return
     */
    List<RecordCrude> pageQueryUserRecord(LocalDate beginTime, LocalDate endTime, String memberName, Long userId);


    /**
     * 根据用户id批量删除诊断记录
     *
     * @param userId
     */
    @Update("update tb_record set delete_flag=true and user_id=#{userId}")
    void batchDeleteByUserId(Long userId);

    /**
     * 根据开始时间和结束时间查询诊断记录
     *
     * @param beginTime
     * @param endTime
     * @param userId
     * @return
     */
    @Select("select * from tb_record where user_id = #{userId} and create_time >= #{beginTime} and create_time <= #{endTime} and delete_flag=false")
    List<Record> getByTime(LocalDate beginTime, LocalDate endTime, Long userId);

    /**
     * 根据id查询详细诊断信息
     * @param id
     * @param userId
     * @return
     */
    RecordCrude getRecordCrudeById(Long id, Long userId);

    RecordCrude getOneRecordCrudeByMemberId(Long memberId, Long userId, LocalDateTime earlyTime);

    RecordCrude getRecordByDoctorId(Long doctorId);
}
