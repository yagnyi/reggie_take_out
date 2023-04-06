package com.oil.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oil.reggie_take_out.common.CustomException;
import com.oil.reggie_take_out.common.R;
import com.oil.reggie_take_out.dto.SetmealDto;
import com.oil.reggie_take_out.entity.Category;
import com.oil.reggie_take_out.entity.Dish;
import com.oil.reggie_take_out.entity.Setmeal;
import com.oil.reggie_take_out.entity.SetmealDish;
import com.oil.reggie_take_out.service.CategoryService;
import com.oil.reggie_take_out.service.DishService;
import com.oil.reggie_take_out.service.SetmealDishService;
import com.oil.reggie_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SetmealDishService setmealDishService;
    @Autowired
    DishService dishService;

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealPage = setmealService.page(setmealPage, setmealLambdaQueryWrapper);

        Page<SetmealDto> setmealDtoPage = new Page<>();
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");

        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> collect = records.stream().map(new Function<Setmeal, SetmealDto>() {
            @Override
            public SetmealDto apply(Setmeal setmeal) {
                SetmealDto setmealDto1 = new SetmealDto();
                BeanUtils.copyProperties(setmeal,setmealDto1);
                String name1 = categoryService.getById(setmeal.getCategoryId()).getName();
                setmealDto1.setCategoryName(name1);
                return setmealDto1;
            }
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(collect);

        return R.success(setmealDtoPage);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        //找到套餐分类名称
        Category category = categoryService.getById(setmeal.getCategoryId());
        setmealDto.setCategoryName(category.getName());
        //找到菜
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> setmealDishList = setmealDishService.list(setmealDishLambdaQueryWrapper);
        setmealDto.setSetmealDishes(setmealDishList);
        return R.success(setmealDto);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.saveWithDish(setmealDto);
        return R.success("添加套餐成功");
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updatWithDish(setmealDto);
        return R.success("修改套餐成功");
    }

    @DeleteMapping
    public R<String> delete(Long[] ids){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = setmealService.count(setmealLambdaQueryWrapper);
        if (count>0) {
            throw new CustomException("套餐正在售卖中");
        }
        for (Long id :ids) {
            setmealService.deleteWithDish(id);
        }
        return R.success("删除套餐成功");
    }

    @PostMapping(value = {"/status/0","/status/1"})
    public R<String> modifyStatus(Long[] ids){
        Setmeal setmeal = setmealService.getById(ids[0]);
        Integer status = setmeal.getStatus();
        for (Long id:
             ids) {
            if (setmealService.getById(id).getStatus()!=status) {
                return R.error("套餐状态不一致");
            }
        }
        for (Long id:
                ids) {
            Setmeal setmeal1 = setmealService.getById(id);
            setmeal1.setStatus(setmeal1.getStatus()==1?0:1);
            setmealService.updateById(setmeal1);
        }
        return R.success("修改套餐状态成功");
    }


    @GetMapping("/list")
    public R<List<Setmeal>> getSetmealList(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        dishLambdaQueryWrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> setmealList = setmealService.list(dishLambdaQueryWrapper);
        return R.success(setmealList);
    }

    @GetMapping("/dish/{id}")
    public R<List<Dish>> getSetmealList(@PathVariable Long  id){
        //R<SetmealDto> setmeal = this.getById(setmealId);
                LambdaQueryWrapper<SetmealDish> setmealDtoLambdaQueryWrapper = new LambdaQueryWrapper<>();
                setmealDtoLambdaQueryWrapper.eq(SetmealDish::getSetmealId,id);
                List<SetmealDish> setmealDishList = setmealDishService.list(setmealDtoLambdaQueryWrapper);

        List<Long> ids = setmealDishList.stream().map(new Function<SetmealDish, Long>() {
            @Override
            public Long apply(SetmealDish setmealDish) {
                return Long.parseLong(setmealDish.getDishId());
            }
        }).collect(Collectors.toList());

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.in(Dish::getId,ids);
        List<Dish> dishList = dishService.list(dishLambdaQueryWrapper);


        return R.success(dishList);
    }
}
