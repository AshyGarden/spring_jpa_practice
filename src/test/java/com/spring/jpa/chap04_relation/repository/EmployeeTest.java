package com.spring.jpa.chap04_relation.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import com.spring.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
public class EmployeeTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    void bulkInsert() {
        Department d1 = Department.builder()
                .name("영업부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();

        departmentRepository.save(d1);
        departmentRepository.save(d2);

        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(d1)
                .build();
        Employee e2 = Employee.builder()
                .name("어피치")
                .department(d1)
                .build();
        Employee e3 = Employee.builder()
                .name("프로도")
                .department(d2)
                .build();
        Employee e4 = Employee.builder()
                .name("춘식이")
                .department(d2)
                .build();

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);

    }

    @Test
    @DisplayName("특정 사원의 정보조회")
    void testFindOne() {
        //given
        long id = 2L;

        //when
        //강제로 사원이 없다고 예외를 발생시킴
        Employee employee
                = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사원이 존재하지 않음")
                );
        //then
        assertEquals("어피치",employee.getName());
    }

    @Test
    @DisplayName("부서 정보 조회")
    void testFindDept() {
        //given
        long id = 1L;
        //when
        Department department
                = departmentRepository.findById(id)
                .orElseThrow();
        //then
        System.out.println("department = " + department);
    }

}
