package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.MemberDTO;
import com.tcm.glossoscopy.entity.po.Member;
import com.tcm.glossoscopy.entity.vo.MemberVO;
import com.tcm.glossoscopy.enums.HealthStatusEnum;
import com.tcm.glossoscopy.mapper.RecordMapper;
import com.tcm.glossoscopy.mapper.MemberMapper;
import com.tcm.glossoscopy.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private MemberMapper memberMapper;
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private RedisCache redisCache;

    /**
     * 添加成员信息
     *
     * @param memberDTO
     */
    @Override
    public void addMemberInfo(MemberDTO memberDTO) {
        //添加成员
        Member member = new Member();
        BeanUtil.copyProperties(memberDTO, member, true);
        LocalDateTime nowTime = LocalDateTime.now();
        member.setUpdateTime(nowTime);
        member.setCreateTime(nowTime);
        member.setDeleteFlag(false);
        if(member.getSex().equals(Constant.GIRL)){
            member.setAvatar(Constant.DEFAULT_GIRL_MEMBER_AVATAR);
        }else{
            member.setAvatar(Constant.DEFAULT_BOY_MEMBER_AVATAR);
        }
        member.setHealthStatus(HealthStatusEnum.NONE_FOR_NOW);
        member.setUserId(UserIdContext.getId());
        memberMapper.addMember(member);
        redisCache.delete(RedisConstant.MEMBER_KEY + UserIdContext.getId());
    }

    /**
     * 根据用户id获取成员信息
     *
     * @return
     */
    @Override
    public List<MemberVO> getUserMemberInfo() {
        List<MemberVO> memberVOList =redisCache.getCollection(RedisConstant.MEMBER_KEY + UserIdContext.getId(), MemberVO.class);
        if(!Objects.isNull(memberVOList)){
            redisCache.expire(RedisConstant.MEMBER_KEY + UserIdContext.getId(),
                    RedisConstant.MEMBER_EXPIRE,
                    TimeUnit.MINUTES);
            return memberVOList;
        }
        //查询该用户所有成员
        List<Member> memberList = memberMapper.getByUserId(UserIdContext.getId());
        memberVOList = new ArrayList<>();
        LocalDate nowTime = LocalDate.now();
        //将成员对象集合封装成VO对象集合
        for (Member member : memberList) {
            MemberVO memberVO = new MemberVO();
            BeanUtil.copyProperties(member, memberVO, true);
            //计算成员年龄
            Integer age = nowTime.getYear() - member.getBirthday().getYear();
            memberVO.setAge(age);
            memberVOList.add(memberVO);
        }
        redisCache.saveCollection(RedisConstant.MEMBER_KEY + UserIdContext.getId(),
                memberVOList,
                RedisConstant.MEMBER_EXPIRE,
                TimeUnit.MINUTES);
        return memberVOList;
    }

    /**
     * 根据成员id获取成员信息
     *
     * @param id
     * @return
     */
    @Override
    public MemberVO getMemberInfo(Long id) {
        //查询成员信息
        Member member = memberMapper.getById(
                Member.builder()
                        .id(id)
                        .userId(UserIdContext.getId())
                        .build());
        //判断是否存在该用户成员，若不存在则返回null
        if (Objects.isNull(member)) {
            return null;
        }
        //将成员对象封装成VO对象
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(member, memberVO, true);
        //计算成员年龄
        Integer age = LocalDate.now().getYear() - member.getBirthday().getYear();
        memberVO.setAge(age);
        return memberVO;
    }


    /**
     * 根据成员id集合批量删除成员信息
     *
     * @param idListDTO
     */
    @Override
    @Transactional
    public void batchDeleteMemberInfo(IdListDTO idListDTO) {
        //批量删除成员对应的诊断记录
        recordMapper.batchDeleteByMemberId(idListDTO.getIdList(), UserIdContext.getId());
        //批量删除成员信息
        memberMapper.batchDeleteById(idListDTO.getIdList(), UserIdContext.getId());
        redisCache.delete(RedisConstant.MEMBER_KEY + UserIdContext.getId());
    }

    /**
     * 根据成员id修改成员信息
     *
     * @param memberDTO
     */
    @Override
    public void updateMemberInfo(MemberDTO memberDTO) {
        //修改成员信息
        Member member = new Member();
        BeanUtil.copyProperties(memberDTO, member, true);
        member.setUserId(UserIdContext.getId());
        member.setUpdateTime(LocalDateTime.now());
        memberMapper.updateInfoById(member);
        redisCache.delete(RedisConstant.MEMBER_KEY + UserIdContext.getId());
    }

}
