package com.example.hw_28_stream_optional.service;

import com.example.hw_28_stream_optional.exceptions.InvalidInputException;
import com.example.hw_28_stream_optional.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EmployeeServiceImplTest {
    private final EmployeeService out = new EmployeeServiceImpl();

    @AfterEach
    public void AfterEach() {
        out.getEmployees().forEach(employee -> out.removeEmployee(employee.getFirstName(), employee.getLastName()));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest1(String firstName, String lastName, int salary, int department) {
        Employee expected = new Employee(firstName, lastName, salary, department);

        assertThat(out.getEmployees()).isEmpty();
        out.addEmployee(firstName, lastName, salary, department);
        assertThat(out.getEmployees())
                .hasSize(1)
                .containsExactly(expected);
        assertThat(out.findEmployee(expected.getFirstName(), expected.getLastName()))
                .isNotNull()
                .isEqualTo(expected);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> out.addEmployee(firstName, lastName, salary, department));
    }

    @Test
    public void addNegativeTest3() {
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> out.addEmployee("Ivan#", "Ivanov", 1, 55_000));

        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> out.addEmployee("Petr", "!Yan", 1, 55_000));

        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> out.addEmployee(null, "Ivanov", 2, 55_000));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeNegativeTest(String firstName, String lastName, int salary, int department) {
        assertThat(out.getEmployees().isEmpty());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> out.removeEmployee("test", "test"));

        Employee expected = new Employee(firstName, lastName, salary, department);
        out.addEmployee(firstName, lastName, salary, department);
        assertThat(out.getEmployees())
                .hasSize(1)
                .containsExactly(expected);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> out.removeEmployee("test", "test"));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removePositiveTest(String firstName, String lastName, int salary, int department) {
        assertThat(out.getEmployees()).isEmpty();
        Employee expected = new Employee(firstName, lastName, salary, department);
        out.addEmployee(firstName, lastName, salary, department);

        assertThat(out.getEmployees())
                .hasSize(1)
                .containsExactly(expected);

        assertThat(out.removeEmployee(firstName, lastName)).isEqualTo(expected);
        assertThat(out.getEmployees()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findNegativeTest(String firstName, String lastName, int salary, int department) {
        assertThat(out.getEmployees()).isEmpty();
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> out.findEmployee("test", "test"));

        Employee expected = new Employee(firstName, lastName, salary, department);
        out.addEmployee(firstName, lastName, salary, department);
        assertThat(out.getEmployees())
                .hasSize(1)
                .containsExactly(expected);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> out.findEmployee("test", "test"));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findPositiveTest(String firstName, String lastName, int salary, int department) {
        assertThat(out.getEmployees()).isEmpty();
        Employee expected = new Employee(firstName, lastName, salary, department);
        out.addEmployee(firstName, lastName, salary, department);

        assertThat(out.getEmployees())
                .hasSize(1)
                .containsExactly(expected);

        assertThat(out.findEmployee(firstName, lastName)).isEqualTo(expected);
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Ivan", "Ivanov", 1, 55_000),
                Arguments.of("Petr", "Yan", 1, 65_000),
                Arguments.of("Maria", "Sharapova", 2, 75_000)
        );
    }
}
