package com.spring.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
//jpa 연관관계 mapping에서 Tostring에서 제외해야함!, 원하는 필드명을 선언시 mapping에서 제외됨!
@ToString(exclude = {"department"})
@EqualsAndHashCode(of= "id")
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long id;

    @Column(name = "emp_name",nullable = false)
    private String name;

    //EAGER: 항상 무조건 조인 수행  VS  LAZY: 필요시에만 Join 수행
    @ManyToOne (fetch = FetchType.LAZY) //(fetch = FetchType.EAGER) - default
    @JoinColumn(name = "dept_id")
    private  Department department; //단방향 연관관계

    public void setDepartment(Department department) {
        this.department = department;
        department.getEmployees().add(this);
    }
}
