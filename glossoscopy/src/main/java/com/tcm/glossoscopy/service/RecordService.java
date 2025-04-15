package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.po.Record;
import com.tcm.glossoscopy.entity.vo.RecordCrudeVO;
import com.tcm.glossoscopy.entity.vo.RecordDetailVO;
import com.tcm.glossoscopy.entity.result.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface RecordService extends IService<Record> {
    /**
     * 根据记录id集合批量删除诊断记录
     *
     * @param idListDTO
     */
    void batchDeleteRecord(IdListDTO idListDTO);

    /**
     * 根据条件分页查询历史诊断记录
     *
     * @param page
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param memberName
     * @return
     */
    PageResult pageQueryUserRecord(Integer page, Integer pageSize, LocalDate beginTime, LocalDate endTime, String memberName);

    /**
     * 根据记录id获取详细诊断记录
     *
     * @param id
     * @return
     */
    RecordDetailVO getRecordDetailInfo(Long id);

    /**
     * 根据记录id获取粗略诊断记录
     * @param id
     * @return
     */
    RecordCrudeVO getRecordCrudeInfo(Long id);

    /**
     * 根据上传图片进行舌诊
     *
     * @param memberId
     * @param url
     * @return
     */
    RecordDetailVO diagnose(Long memberId, String url);

    RecordCrudeVO getRandomRecord();
}
