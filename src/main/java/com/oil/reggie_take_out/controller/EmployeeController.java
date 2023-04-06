package com.oil.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oil.reggie_take_out.common.R;
import com.oil.reggie_take_out.entity.Employee;
import com.oil.reggie_take_out.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request , @RequestBody Employee employee){
        String password = employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(employeeLambdaQueryWrapper);

        if(emp==null)
            return R.error("登录失败");

        if(!emp.getPassword().equals(password))
            return R.error("登录失败");

        if(emp.getStatus()==0)
            return R.error("账号被禁用");

        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(new Date());
        //employee.setUpdateTime(new Date());
        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize ,String name){
        Page pageInfo = new Page();
        LambdaQueryWrapper<Employee> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        LambdaQueryWrapper.orderByDesc(Employee::getCreateTime);
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        employeeService.page(pageInfo,LambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        //log.info(employee.toString());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        //employee.setUpdateTime(new Date());
        employeeService.updateById(employee);
        return R.success("更新状态成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(HttpServletRequest request,@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee!=null)
        return R.success(employee);

        return R.error("没有此员工");
    }
}
