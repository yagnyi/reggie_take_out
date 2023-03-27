package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.DishFlavor;
import com.oil.reggie_take_out.service.DishFlavorService;
import com.oil.reggie_take_out.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




