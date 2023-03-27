package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.Category;
import com.oil.reggie_take_out.service.CategoryService;
import com.oil.reggie_take_out.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




