package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.UserReviewDTO;
import com.tcm.glossoscopy.entity.po.UserReview;

public interface UserReviewService extends IService<UserReview> {
    /**
     * 新增用户评价
     * @param userReviewDTO
     */
    void addUserReview(UserReviewDTO userReviewDTO);
}
