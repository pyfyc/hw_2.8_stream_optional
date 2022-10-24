package com.example.hw_28_stream_optional.service;

import com.example.hw_28_stream_optional.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Maria", "Sharapova", 80_000, 2),
                new Employee("Vasya", "Pupkin", 10_000, 1),
                new Employee("Oleg", "Ivanov", 20_000, 1),
                new Employee("Rafa", "Nadal", 100_000, 2),
                new Employee("Roger", "Federer", 120_000, 2),
                new Employee("Ivan", "Urgant", 30_000, 1)
        );
        when(employeeService.getEmployees()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void highestPaidEmployeePositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.getHighestPaidEmployee(departmentId)).isEqualTo(expected);
    }

    @Test
    public void highestPaidEmployeeNegativeTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> departmentService.getHighestPaidEmployee(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void lowestPaidEmployeePositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.getLowestPaidEmployee(departmentId)).isEqualTo(expected);
    }

    @Test
    public void lowestPaidEmployeeNegativeTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> departmentService.getLowestPaidEmployee(3));
    }

    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeesForDepartmentPositiveTest(int departmentId, List<Employee> expected) {
        assertThat(departmentService.printEmployeesForDepartment(departmentId)).containsExactlyElementsOf(expected);
    }

    @Test
    public void employeesByDepartmentsTest() {
        assertThat(departmentService.printEmployeesByDepartments()).containsExactlyElementsOf(
                List.of(
                        new Employee("Vasya", "Pupkin", 10_000, 1),
                        new Employee("Oleg", "Ivanov", 20_000, 1),
                        new Employee("Ivan", "Urgant", 30_000, 1),
                        new Employee("Maria", "Sharapova", 80_000, 2),
                        new Employee("Rafa", "Nadal", 100_000, 2),
                        new Employee("Roger", "Federer", 120_000, 2)
                )
        );
    }

    public static Stream<Arguments> employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Ivan", "Urgant", 30_000, 1)),
                Arguments.of(2, new Employee("Roger", "Federer", 120_000, 2))
        );
    }

    public static Stream<Arguments> employeeWithMinSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Vasya", "Pupkin", 10_000, 1)),
                Arguments.of(2, new Employee("Maria", "Sharapova", 80_000, 2))
        );
    }

    public static Stream<Arguments> employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(
                        new Employee("Vasya", "Pupkin", 10_000, 1),
                        new Employee("Oleg", "Ivanov", 20_000, 1),
                        new Employee("Ivan", "Urgant", 30_000, 1))),
                Arguments.of(2, List.of(
                        new Employee("Maria", "Sharapova", 80_000, 2),
                        new Employee("Rafa", "Nadal", 100_000, 2),
                        new Employee("Roger", "Federer", 120_000, 2))),
                Arguments.of(3, Collections.emptyList())
        );
    }
}
