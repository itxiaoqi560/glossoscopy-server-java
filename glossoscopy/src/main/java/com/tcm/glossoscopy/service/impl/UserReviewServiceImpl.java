package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.UserReviewDTO;
import com.tcm.glossoscopy.entity.po.UserReview;
import com.tcm.glossoscopy.mapper.UserReviewMapper;
import com.tcm.glossoscopy.service.UserReviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class UserReviewServiceImpl extends ServiceImpl<UserReviewMapper, UserReview> implements UserReviewService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private UserReviewMapper userReviewMapper;
    /**
     * 新增用户评价
     * @param userReviewDTO
     */
    @Override
    public void addUserReview(UserReviewDTO userReviewDTO) {
        UserReview userReview = BeanUtil.copyProperties(userReviewDTO, UserReview.class);
        userReview.setUserId(UserIdContext.getId());
        userReview.setDoctorId(redisCache.get(RedisConstant.CHAT_DOCTOR_KEY+userReviewDTO.getReceiverUUID(),Long.TYPE));
        userReview.setCreateTime(LocalDateTime.now());
        userReviewMapper.addUserReview(userReview);
    }
}
