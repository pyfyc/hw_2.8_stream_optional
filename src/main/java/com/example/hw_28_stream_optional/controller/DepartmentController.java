package com.example.hw_28_stream_optional.controller;

import com.example.hw_28_stream_optional.model.Employee;
import com.example.hw_28_stream_optional.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(path = "/max-salary")
    public Object getHighestPaidEmployee(
            @RequestParam(value = "departmentId") int departmentId) {
        Employee employee = null;
        try {
            employee = departmentService.getHighestPaidEmployee(departmentId);
        } catch (Throwable e) {
            return e.getMessage();
        }
        return employee;
    }

    @GetMapping(path = "/min-salary")
    public Object getLowestPaidEmployee(
            @RequestParam(value = "departmentId") int departmentId) {
        Employee employee = null;
        try {
            employee = departmentService.getLowestPaidEmployee(departmentId);
        } catch (Throwable e) {
            return e.getMessage();
        }
        return employee;
    }

    @GetMapping(path = "/all", params = "departmentId")
    public Object printEmployeesForDepartment(
            @RequestParam(value = "departmentId") int departmentId) {
        List<Employee> employees = null;
        try {
            employees = departmentService.printEmployeesForDepartment(departmentId);
        } catch (Throwable e) {
            return e.getMessage();
        }
        return employees;
    }

    @GetMapping(path = "/all")
    public Object printEmployeesByDepartments() {
        List<Employee> employees = null;
        try {
            employees = departmentService.printEmployeesByDepartments();
        } catch (Throwable e) {
            return e.getMessage();
        }
        return employees;
    }

}
