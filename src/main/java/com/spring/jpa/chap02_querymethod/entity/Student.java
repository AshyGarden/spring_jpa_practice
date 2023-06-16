package com.spring.jpa.chap02_querymethod.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter //실무적 측면에서 setter는 신중하게 만들 것
@Getter @ToString
@EqualsAndHashCode(of="id") //아이디만 같으면 같은 객체로 인식하게 설정
//@EqualsAndHashCode(of={"name","id"}) //이름+아이디가 같으면 같은 객체로 인식하게 설정
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @Column(name="stu_id") //변수가 stuId가 아닐경우는 name선언이 필수
    @GeneratedValue(generator = "uid") //uuid사용시 genericgenerator의 name을 지목!
    @GenericGenerator(strategy = "uuid", name = "uid") //strategy는 필수, name은 선택
    private  String id;

    @Column(name = "stu_name", nullable = false)
    private  String name;
    private  String city;
    private  String major;




}
