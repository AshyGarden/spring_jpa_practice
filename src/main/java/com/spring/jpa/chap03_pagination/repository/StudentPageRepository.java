package com.spring.jpa.chap03_pagination.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPageRepository extends JpaRepository<Student, String> {

    //학생 조건 없이 전체 조회 페이징(기본기능->안 만들어도 됨)

   Page<Student> findByNameContaining(String name, Pageable pageable);

}
