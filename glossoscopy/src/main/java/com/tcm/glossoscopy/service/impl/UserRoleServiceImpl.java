package com.tcm.glossoscopy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.entity.po.UserRole;
import com.tcm.glossoscopy.mapper.UserRoleMapper;
import com.tcm.glossoscopy.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}