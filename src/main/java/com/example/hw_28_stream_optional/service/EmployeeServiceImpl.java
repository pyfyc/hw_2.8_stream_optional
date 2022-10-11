package com.example.hw_28_stream_optional.service;

import com.example.hw_28_stream_optional.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final List<Employee> employees;
    private final String ERR_EMPL_ALREADY_ADDED = "Сотрудник уже имеется в массиве";
    private final String ERR_EMPL_NOT_FOUND = "Сотрудник не найден";
    private final String ERR_INVALID_NAME = "Неверное имя/фамилия";

    public EmployeeServiceImpl(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public Employee addEmployee(String firstName, String lastName, int salary, int department) {
        if (!validateName(firstName) || !validateName(lastName)) {
            throw new RuntimeException(ERR_INVALID_NAME);
        }
        Employee employee = new Employee(capFirstLetter(firstName), capFirstLetter(lastName), salary, department);
        if (employees.contains(employee)) {
            throw new RuntimeException(ERR_EMPL_ALREADY_ADDED);
        }
        employees.add(employee);
        return employee;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = findEmployee(firstName, lastName);
        employees.remove(employee);
        return employee;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        final Optional<Employee> employee = employees.stream()
                .filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                .findAny();
        return employee.orElseThrow(() -> new RuntimeException(ERR_EMPL_NOT_FOUND));
    }

    @Override
    public Employee getLowestPaidEmployee(int department) {
        return employees.stream()
                .filter(e -> e.getDepartment() == department)
                .min(Comparator.comparingInt(e -> e.getSalary()))
                .orElseThrow(() -> new RuntimeException(ERR_EMPL_NOT_FOUND));
    }

    @Override
    public Employee getHighestPaidEmployee(int department) {
        return employees.stream()
                .filter(e -> e.getDepartment() == department)
                .max(Comparator.comparingInt(e -> e.getSalary()))
                .orElseThrow(() -> new RuntimeException(ERR_EMPL_NOT_FOUND));
    }

    @Override
    public List<Employee> printEmployeesForDepartment(int department) {
        return employees.stream()
                .filter(e -> e.getDepartment() == department)
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> printEmployeesByDepartments() {
        return Collections.unmodifiableList(employees.stream()
                .sorted(Comparator.comparingInt(e -> e.getDepartment()))
                .collect(Collectors.toList()));
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

    private boolean validateName(String name) {
        char[] invalidChars = "1234567890!@#$%^&*()_-=".toCharArray();
        return StringUtils.containsNone(name, invalidChars);
    }

    private String capFirstLetter(String str) {
        return StringUtils.upperCase(str.substring(0, 1)) + str.substring(1);
    }
}
