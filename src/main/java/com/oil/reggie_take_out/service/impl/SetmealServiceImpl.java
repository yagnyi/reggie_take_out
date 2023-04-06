package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.common.CustomException;
import com.oil.reggie_take_out.dto.SetmealDto;
import com.oil.reggie_take_out.entity.Setmeal;
import com.oil.reggie_take_out.entity.SetmealDish;
import com.oil.reggie_take_out.service.SetmealDishService;
import com.oil.reggie_take_out.service.SetmealService;
import com.oil.reggie_take_out.mapper.SetmealMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    SetmealDishService setmealDishService;
    public void saveWithDish(SetmealDto setmealDto){

        this.save(setmealDto);

        setSetmealDish(setmealDto);


    }

    @Override
    public void updatWithDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(setmealDto.getId()!=null,SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(setmealDishLambdaQueryWrapper);

        setSetmealDish(setmealDto);
    }

    @Override
    public void deleteWithDish(Long ids) {



        Setmeal setmeal = this.getById(ids);

        this.removeById(setmeal);

        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealDishLambdaQueryWrapper.eq(setmeal.getId()!=null,SetmealDish::getSetmealId,setmeal.getId());

        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }

    public void setSetmealDish(SetmealDto setmealDto){

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        log.info(setmealDishes.toString());
        List<SetmealDish> collect = setmealDishes.stream().map(new Function<SetmealDish, SetmealDish>() {
            @Override
            public SetmealDish apply(SetmealDish setmealDish) {
                setmealDish.setSetmealId(setmealDto.getId().toString());
                return setmealDish;
            }
        }).collect(Collectors.toList());
        log.info(setmealDishes.toString());
        setmealDishService.saveBatch(collect);
    }


}




