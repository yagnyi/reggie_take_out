package com.oil.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oil.reggie_take_out.entity.Employee;
import com.oil.reggie_take_out.service.EmployeeService;
import com.oil.reggie_take_out.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-03-22 16:17:45
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




