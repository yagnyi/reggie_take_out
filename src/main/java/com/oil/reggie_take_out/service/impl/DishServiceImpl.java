package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.dto.DishDto;
import com.oil.reggie_take_out.entity.Dish;
import com.oil.reggie_take_out.entity.DishFlavor;
import com.oil.reggie_take_out.service.DishFlavorService;
import com.oil.reggie_take_out.service.DishService;
import com.oil.reggie_take_out.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.soap.Addressing;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFilvor(DishDto dishDto) {
        this.save(dishDto);

        //保存口味表
        setDishFlavors(dishDto);

    }

    /**
     * 根据id获得 菜品和其中的口味
     * @param id
     * @return
     */
    public DishDto getDishDtoById(Long id){
        Dish dish = this.getById(id);
        Long dishId = dish.getId();
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(dishId!=null,DishFlavor::getDishId,dishId);
        List<DishFlavor> dishFlavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(dishFlavors);

        return dishDto;

    }

    /**
     * 保存口味表
     * @param dishDto
     */
    public void setDishFlavors(DishDto dishDto){
        Long dtoId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();

        List<DishFlavor> dishFlavorList = flavors.stream().map(Flavor -> {
            Flavor.setDishId(dtoId);
            return Flavor;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(dishFlavorList);
    }

    @Override
    public void updateDishDto(DishDto dishDto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        this.updateById(dish);
        //先删除原来的口味表
        Long dishDtoId = dishDto.getId();
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(dishDtoId!=null,DishFlavor::getDishId,dishDtoId);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        //再添加新的口味表
        //List<DishFlavor> flavors = dishDto.getFlavors();
        //dishFlavorService.updateBatchById(flavors);
        setDishFlavors(dishDto);

    }

    @Override
    public void deleteDishDto(Long ids) {
        this.removeById(ids);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(ids!=null,DishFlavor::getDishId,ids);
//        List<DishFlavor> list = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
//        List<Long> collect = list.stream().map(dishFlavor -> dishFlavor.getDishId()).collect(Collectors.toList());
//        dishFlavorService.removeByIds(collect);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
    }
}




