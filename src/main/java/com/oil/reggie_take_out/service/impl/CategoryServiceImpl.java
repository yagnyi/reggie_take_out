package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.common.CustomException;
import com.oil.reggie_take_out.common.R;
import com.oil.reggie_take_out.entity.Category;
import com.oil.reggie_take_out.entity.Dish;
import com.oil.reggie_take_out.entity.Setmeal;
import com.oil.reggie_take_out.service.CategoryService;
import com.oil.reggie_take_out.mapper.CategoryMapper;
import com.oil.reggie_take_out.service.DishFlavorService;
import com.oil.reggie_take_out.service.DishService;
import com.oil.reggie_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        //是否关联
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(dishQueryWrapper);
        if(dishCount>0){
            throw new CustomException("菜品关联");
        }
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int setmealCount = dishService.count(dishQueryWrapper);
        if(setmealCount>0){
            throw new CustomException("套餐关联");
        }




        super.removeById(id);
    }
}




