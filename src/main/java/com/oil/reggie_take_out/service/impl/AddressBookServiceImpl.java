package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.AddressBook;
import com.oil.reggie_take_out.service.AddressBookService;
import com.oil.reggie_take_out.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




