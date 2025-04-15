package com.tcm.glossoscopy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.entity.po.Role;
import com.tcm.glossoscopy.mapper.RoleMapper;
import com.tcm.glossoscopy.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}