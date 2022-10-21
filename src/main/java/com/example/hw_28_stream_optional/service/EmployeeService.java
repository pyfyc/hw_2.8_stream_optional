package com.example.hw_28_stream_optional.service;

import com.example.hw_28_stream_optional.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(String firstName, String lastName, int salary, int department);
    Employee removeEmployee(String firstName, String lastName);
    Employee findEmployee(String firstName, String lastName);
    List<Employee> printEmployees();
    List<Employee> fillEmployeesList();
}
