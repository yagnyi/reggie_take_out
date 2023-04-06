package com.oil.reggie_take_out.service;

import com.oil.reggie_take_out.dto.DishDto;
import com.oil.reggie_take_out.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oil.reggie_take_out.entity.DishFlavor;

import java.util.List;

/**
* @author Administrator
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-03-22 16:17:45
*/
public interface DishService extends IService<Dish> {

    void saveWithFilvor (DishDto dishDto);
    DishDto getDishDtoById(Long id);
    void updateDishDto(DishDto dishDto);

    void deleteDishDto(Long ids);
}
