package com.spring.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(exclude = {"employees"})
@EqualsAndHashCode(of= "id")
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_dept")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private  long id;

    @Column(name = "dept_name", nullable = false)
    private  String name;

    //양방향 Mapping - 상대방 Entiy 갱신에 관여할수 없다.
    //단순 읽기전용으로만 사용해야한다.
    //mappedby에는 상대방 Entity의 조인되는 필드명을 작성
    @OneToMany(mappedBy = "department") //onetomany는 join column 주지 않는다!(읽기전용)
    private List<Employee> employees = new ArrayList<>(); //초기화(Nullpointer exception 방지)

}
