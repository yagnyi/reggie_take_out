package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.ShoppingCart;
import com.oil.reggie_take_out.service.ShoppingCartService;
import com.oil.reggie_take_out.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




