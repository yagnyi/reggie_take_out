package com.oil.reggie_take_out.controller;

import com.oil.reggie_take_out.common.R;
import com.oil.reggie_take_out.entity.Employee;
import com.oil.reggie_take_out.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeContrlller {
    @Autowired
    private EmployeeService employeeService;

    @PatchMapping("/login")
    public R<Employee> login(HttpRequest httpRequest , @RequestBody Employee employee){
        
        return null;
    }
}
