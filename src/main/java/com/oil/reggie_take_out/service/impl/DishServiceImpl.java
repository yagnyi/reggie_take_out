package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.Dish;
import com.oil.reggie_take_out.service.DishService;
import com.oil.reggie_take_out.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

}




