package com.example.hw_28_stream_optional.service;

import com.example.hw_28_stream_optional.model.Employee;

import java.util.List;

public interface DepartmentService {
    Employee getLowestPaidEmployee(int department);

    Employee getHighestPaidEmployee(int department);

    List<Employee> printEmployeesForDepartment(int department);

    List<Employee> printEmployeesByDepartments();
}
