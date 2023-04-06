package com.oil.reggie_take_out.service;

import com.oil.reggie_take_out.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-03-22 16:17:45
*/
public interface CategoryService extends IService<Category> {

    void remove(Long id);

}
