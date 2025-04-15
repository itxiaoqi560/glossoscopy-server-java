package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.MemberDTO;
import com.tcm.glossoscopy.entity.po.Member;
import com.tcm.glossoscopy.entity.vo.MemberVO;

import java.util.List;

public interface MemberService extends IService<Member> {


    /**
     * 添加成员信息
     * @param memberDTO
     */
    void addMemberInfo(MemberDTO memberDTO);

    /**
     * 根据用户id获取成员信息
     * @return
     */
    List<MemberVO> getUserMemberInfo();

    /**
     * 根据成员id获取成员信息
     * @param id
     * @return
     */
    MemberVO getMemberInfo(Long id);

    /**
     * 根据成员id集合批量删除成员信息
     * @param idListDTO
     */
    void batchDeleteMemberInfo(IdListDTO idListDTO);

    /**
     * 根据成员id修改成员信息
     * @param memberDTO
     */
    void updateMemberInfo(MemberDTO memberDTO);
}
