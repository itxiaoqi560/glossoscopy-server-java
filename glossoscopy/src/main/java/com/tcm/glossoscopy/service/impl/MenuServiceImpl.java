package com.tcm.glossoscopy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.entity.po.Menu;
import com.tcm.glossoscopy.mapper.MenuMapper;
import com.tcm.glossoscopy.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}