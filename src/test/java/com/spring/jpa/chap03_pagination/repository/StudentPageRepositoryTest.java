package com.spring.jpa.chap03_pagination.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert() {
        //학생을 147명 저장
        for(int i=1; i<=147; i++) {
            Student s = Student.builder()
                    .name("김테스트" +  i)
                    .city("도시시" + i)
                    .major("전공공" + i)
                    .build();
            studentPageRepository.save(s);
        }
    }
    @Test
    @DisplayName("기본 페이징 테스트")
    void testBasicPagination() {
        //given
        int pageNo = 15;
        int amount = 10;

        //when
        //페이지 번호가 zero-based
        Pageable pageInfo = PageRequest.of(pageNo-1, amount,
                Sort.by(
                        Sort.Order.desc("name"),
                        Sort.Order.asc("city")));

        //then
        Page<Student> students = studentPageRepository.findAll(pageInfo);
        List<Student> studentList = students.getContent();

        //총 학생수
        long totalElements = students.getTotalElements();
        //총페이지수
        int totalPage = students.getTotalPages();

        boolean next = students.hasNext();
        boolean prev = students.hasPrevious();


        System.out.println("\n\n\n");
        System.out.println("totalPage = " + totalPage);
        System.out.println("totalElements = " + totalElements);
        System.out.println("prev = " + prev);
        System.out.println("next = " + next);
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("이름검색 + 페이징 구현")
    void testSearchAndPagination() {
        //given
        int pageNo=15;
        int size =10;
        Pageable pageInfo = PageRequest.of(pageNo-1,size);

        //when
        Page<Student> students
                = studentPageRepository.findByNameContaining("3", pageInfo);
        //총페이지수
        int totalPage = students.getTotalPages();

        //총 학생수
        long totalElements = students.getTotalElements();

        //then
        System.out.println("\n\n\n");
        System.out.println("totalElements = " + totalElements);
        System.out.println("totalPage = " + totalPage);
        students.getContent().forEach(System.out::println);
        System.out.println("\n\n\n");


    }

}
