package com.spring.jpa.chap01_basic.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private long id;

    @Column(name = "prod_name", nullable = false, length = 30)
    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp //타임스탬프 만들기
    @Column(updatable = false) //수정을 막는 문구
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate; //업데이트시에 작동

    public enum Category{
        FOOD, FASHION, ELECTRONIC
    }
}