package com.spring.jpa.chap02_querymethod.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
public class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData(){
        Student s1 = Student.builder()
                .name("띠오니")
                .city("서울시")
                .major("디자인과")
                .build();
        Student s2 = Student.builder()
                .name("동준신")
                .city("서울시")
                .major("유아교육과")
                .build();
        Student s3 = Student.builder()
                .name("갓태호")
                .city("서울시")
                .major("전기과")
                .build();
        Student s4 = Student.builder()
                .name("용준이")
                .city("서울시")
                .major("기계과")
                .build();
        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
        studentRepository.save(s4);
    }

    @Test
    @DisplayName("이름이 띠오니면 정보를 보여주세요")
    void testFindByName() {
        //given
        String name = "띠오니";

        //when
        List<Student> students = studentRepository.findByName(name);

        //then
        assertEquals(1,students.size());

        System.out.println("students.get(0) = " + students.get(0));
    }

    @Test
    @DisplayName("testFindByCityAndMajor")
    void testFindByCityAndMajor() {
        //given
        String city = "서울시";
        String major = "유아교육과";
        //when
        List<Student> students = studentRepository.findByCityAndMajor(city,major);

        //then
        assertEquals(1,students.size());
        assertEquals("동준신",students.get(0).getName());

        System.out.println("students.get(0) = " + students.get(0));
    }

    @Test
    @DisplayName("testFindByMajorContaining")
    void testFindByMajorContaining() {
        //give
        String major = "기계과";
        //when
        List<Student> students = studentRepository.findByMajorContaining(major);

        //then
        assertEquals(1,students.size());
        students.forEach(System.out::println); //해당되는 모든 객체를 출력
    }

    @Test
    @DisplayName("testNativeSQL")
    void testNativeSQL() {
        //give
        String name = "갓태호";
        //when
        Student student = studentRepository.findNameWithSQL(name);

        //then
        assertEquals("서울시",student.getCity());
        System.out.println("student = " + student);
    }

    @Test
    @DisplayName("testFindByCityWithSQL")
    void testFindByCityWithSQL() {
        //give
        String city = "서울시";
        //when
        List<Student> students = studentRepository.getByCityWithJPQL(city);

        //then
        assertEquals("서울시",students.get(0).getCity());
        //System.out.println("students = " + students);
    }

    @Test
    @DisplayName("testSearchByNamesWithJPQL")
    void testSearchByNamesWithJPQL() {
        //give
        String name = "준";
        //when
        List<Student> students = studentRepository.searchByNamesWithJPQL(name);

        //then
        assertEquals(2,students.size());
        //System.out.println("students = " + students);
    }

    @Test
    @DisplayName("JPQL로 삭제하기")
    void testDeleteWithJPQL() {
        //given
        String name = "띠오니";
        //when
        studentRepository.deleteByNameWithJPQL(name);
        //then
        List<Student> students = studentRepository.findByName(name);

        assertEquals(0,students.size());
    }

}
