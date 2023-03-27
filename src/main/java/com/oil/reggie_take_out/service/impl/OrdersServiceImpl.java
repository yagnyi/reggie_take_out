package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.Orders;
import com.oil.reggie_take_out.service.OrdersService;
import com.oil.reggie_take_out.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




