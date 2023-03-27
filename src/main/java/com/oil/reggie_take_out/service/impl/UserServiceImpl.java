package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.User;
import com.oil.reggie_take_out.service.UserService;
import com.oil.reggie_take_out.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




