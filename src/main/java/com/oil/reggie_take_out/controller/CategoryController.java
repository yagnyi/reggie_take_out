package com.oil.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oil.reggie_take_out.common.R;
import com.oil.reggie_take_out.entity.Category;
import com.oil.reggie_take_out.entity.Employee;
import com.oil.reggie_take_out.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;



    @GetMapping("/page")
    public R<Page> page(int page,int pageSize ,String name){
        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Category> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        //LambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Category::getName,name);
        LambdaQueryWrapper.orderByDesc(Category::getSort);
//        pageInfo.setCurrent(page);
//        pageInfo.setSize(pageSize);
        categoryService.page(pageInfo,LambdaQueryWrapper);
        return R.success(pageInfo);
    }


    @PostMapping
    public R<Category> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success(category);
    }


    @PutMapping
    public R<Category> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success(category);
    }


    @DeleteMapping
    public R<String> delete(@RequestParam Long ids){
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Category>> getItemList(Category category){
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (category.getType()!=null) {
            categoryLambdaQueryWrapper.eq(Category::getType,category.getType());
        }

        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }
 }
