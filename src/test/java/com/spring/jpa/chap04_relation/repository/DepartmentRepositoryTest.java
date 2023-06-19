package com.spring.jpa.chap04_relation.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import com.spring.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class DepartmentRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("특정부서 조회시 부서원들도 함께 조회 되어야 한다.")
    void testFindDept() {
        //given
        long id = 2L;
        //when
        Department department = departmentRepository.findById(id).orElseThrow();
        //then

        System.out.println();
        System.out.println("department = " + department);
        System.out.println("department.getEmployees() = " + department.getEmployees());
        System.out.println();
    }

    @Test
    @DisplayName("lazy loding VS Eager loading")
    void testLazyLoading() {
        //3번 사원 조회시, 부서정보는 필요없음
        //given
        long id = 3L;
        //when
        Employee employee 
                = employeeRepository.findById(id).orElseThrow();
        //then
        System.out.println();
        System.out.println("employee = " + employee);
        System.out.println();
    }
    
    @Test
    @DisplayName("양방향 완관관계에서 연관 데이터의 수정")
    void testChangeDept() {
        //3번 사원의 부서를 2번 부서에서 1번부서로 변경
        //given
        long id =  3L;
        //when
        Employee foundEmp = employeeRepository.findById(id).orElseThrow();

        Department newDept = departmentRepository.findById(1L).orElseThrow();

        //사원의 부서정보를 업데이트 하면서, 부서에 대한 정보로 같이 업데이트
        foundEmp.setDepartment(newDept);

        employeeRepository.save(foundEmp);

        //insert, update, delete를 transaction commit후 발동시키게함
        //=save가 맨 마지막에 진행됨! - 출력문에는 정보가 이전정보로 전달됨!

        //변경감지(dirty check) = 변경된 내용을 DB에 바로 반영
        //entityManager.flush();
        //entityManager.clear();


        //then
        //1번부서정보를 조회해서 모든사람을 보낸다.
        Department foundDept
                = departmentRepository.findById(1L).orElseThrow();

        System.out.println();
        foundDept.getEmployees().forEach(System.out::println);
        System.out.println();
    }

    @Test
    @DisplayName("N+1 문제 발생 예시")
    void testNPlus1Ex() {
        //given
        List<Department> departments = departmentRepository.findAll();

        //when
        departments.forEach(dept->{
            System.out.println("\n\n====== 사원 리스트======");

            List<Employee> employees = dept.getEmployees();
            System.out.println("employees = " + employees);

            System.out.println("\n\n");
        });

        //then


    }

    @Test
    @DisplayName("N+1 문제 해결 예시")
    void testNPlus1Solution() {
        //given
        List<Department> departments = departmentRepository.findAllIncludeEmployees();

        //when
        departments.forEach(dept->{
            System.out.println("\n\n====== 사원 리스트======");

            List<Employee> employees = dept.getEmployees();
            System.out.println("employees = " + employees);

            System.out.println("\n\n");
        });
        //then
    }
}
