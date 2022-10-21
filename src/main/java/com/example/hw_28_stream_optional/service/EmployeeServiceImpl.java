package com.example.hw_28_stream_optional.service;

import com.example.hw_28_stream_optional.exceptions.InvalidInputException;
import com.example.hw_28_stream_optional.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isAlpha;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final List<Employee> employees;

    public EmployeeServiceImpl(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public Employee addEmployee(String firstName, String lastName, int salary, int department) {
        validateInput(firstName, lastName);

        Employee employee = new Employee(firstName, lastName, salary, department);
        if (employees.contains(employee)) {
            throw new RuntimeException(Constants.ERR_EMPLOYEE_ALREADY_ADDED);
        }
        employees.add(employee);
        return employee;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        validateInput(firstName, lastName);
        Employee employee = findEmployee(firstName, lastName);
        employees.remove(employee);
        return employee;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        validateInput(firstName, lastName);
        final Optional<Employee> employee = employees.stream()
                .filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                .findAny();
        return employee.orElseThrow(() -> new RuntimeException(Constants.ERR_EMPLOYEE_NOT_FOUND));
    }

    @Override
    public List<Employee> printEmployees() {
        return Collections.unmodifiableList(employees);
    }

    @Override
    public List<Employee> fillEmployeesList() {
        employees.add(new Employee("Maria", "Sharapova", 80_000, 2));
        employees.add(new Employee("Vasya", "Pupkin", 10_000, 1));
        employees.add(new Employee("Oleg", "Ivanov", 20_000, 1));
        employees.add(new Employee("Rafa", "Nadal", 100_000, 2));
        employees.add(new Employee("Roger", "Federer", 120_000, 2));
        employees.add(new Employee("Ivan", "Urgant", 30_000, 1));
        return employees;
    }

    private void validateInput(String firstName, String lastName) {
        if (!(isAlpha(firstName) && isAlpha(lastName))) {
            throw new InvalidInputException(Constants.ERR_INVALID_NAME);
        }
    }
}
