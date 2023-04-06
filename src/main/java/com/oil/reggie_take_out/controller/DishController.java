package com.oil.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oil.reggie_take_out.common.R;
import com.oil.reggie_take_out.dto.DishDto;
import com.oil.reggie_take_out.entity.Category;
import com.oil.reggie_take_out.entity.Dish;
import com.oil.reggie_take_out.entity.DishFlavor;
import com.oil.reggie_take_out.service.CategoryService;
import com.oil.reggie_take_out.service.DishFlavorService;
import com.oil.reggie_take_out.service.DishService;
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
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage,dishLambdaQueryWrapper);

        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");

        List<Dish> dishRecords = dishPage.getRecords();
        List<DishDto> dishDtosRecords = dishRecords.stream().map(new Function<Dish, DishDto>() {
            @Override
            public DishDto apply(Dish dish) {
                DishDto dishDto = new DishDto();
                BeanUtils.copyProperties(dish,dishDto);
                Category category = categoryService.getById(dish.getCategoryId());
                if(category!=null){
                    String name1 = category.getName();
                    dishDto.setCategoryName(name1);
                }

                return dishDto;
            }
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtosRecords);
        return R.success(dishDtoPage);
    }


    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFilvor(dishDto);
        return R.success("添加菜品成功！");
    }


    @GetMapping("/{id}")
    public R<DishDto> getDishDto(@PathVariable Long id){
        DishDto dishDto = (DishDto) dishService.getDishDtoById(id);
        return R.success(dishDto);
    }



    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateDishDto(dishDto);
        return R.success("更新菜品成功");
    }

//    //@DeleteMapping
//    public R<String> delete(Long ids){
//        dishService.deleteDishDto(ids);
//        return R.success("删除菜品成功");
//    }

    @PostMapping(value = {"/status/0","/status/1"})//?ids=1641824394201939970
    public R<String> modifyDishStatus(Long[] ids){
        Integer status = dishService.getDishDtoById(ids[0]).getStatus();
        for (Long id:ids) {
            Dish dish = dishService.getById(id);
            if (dish.getStatus()!=status)
            return R.error("选中菜品状态不一致");
        }
        for (Long id:ids) {
            Dish dish = dishService.getById(id);
                dish.setStatus(dish.getStatus()==1?0:1);
                dishService.updateById(dish);
        }

        return R.success("修改菜品状态成功");
    }

    @DeleteMapping
    public R<String> delete1(Long[] ids){
        for (Long id:ids ){
            dishService.deleteDishDto(id);
        }
        return R.success("批量删除菜品成功");
    }



//    @GetMapping("/list")
//    public R<List<Dish>> getDishList(Dish dish){
//        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        dishLambdaQueryWrapper.eq(Dish::getStatus,dish.getStatus());
//        List<Dish> dishList = dishService.list(dishLambdaQueryWrapper);
//        return R.success(dishList);
//    }


    @GetMapping("/list")
    public R<List<DishDto>> getDishList(Dish dish){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        dishLambdaQueryWrapper.eq(dish.getStatus()!=null,Dish::getStatus,dish.getStatus());
        List<Dish> dishList = dishService.list(dishLambdaQueryWrapper);

        List<DishDto> dishDtoList = dishList.stream().map(dish1 -> {
                                                    DishDto dishDto = new DishDto();
                                                    BeanUtils.copyProperties(dish1,dishDto);
                                                    LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                                    dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dish1.getId());
                                                    List<DishFlavor> flavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
                                                    dishDto.setFlavors(flavorList);
                                                    return dishDto;
                                                }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
