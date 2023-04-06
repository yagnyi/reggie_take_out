package com.oil.reggie_take_out.service;

import com.oil.reggie_take_out.dto.SetmealDto;
import com.oil.reggie_take_out.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-03-22 16:17:45
*/
public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);
    void updatWithDish(SetmealDto setmealDto);
    void deleteWithDish(Long ids);
}
