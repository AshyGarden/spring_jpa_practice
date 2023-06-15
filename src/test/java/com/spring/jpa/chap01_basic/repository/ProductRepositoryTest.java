package com.spring.jpa.chap01_basic.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.jpa.chap01_basic.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void insertdummyData(){
        //given
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(1200000)
                .build();
        Product p2 = Product.builder()
                .name("노트북")
                .category(ELECTRONIC)
                .price(2200000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(120000)
                .build();
        Product p4 = Product.builder()
                .name("garbage")
                .category(FASHION)
                .build();
        //when
        Product save1 = productRepository.save(p1);
        Product save2 = productRepository.save(p2);
        Product save3 = productRepository.save(p3);
        Product save4 = productRepository.save(p4);

        //then
        assertNotNull(save1);
        assertNotNull(save3);
    }

    @Test
    @DisplayName("5번째 상품을 DB에 저장하기")
    void testSave() {
        Product product = Product.builder()
                .name("정장")
                .price(1000000)
                .category(FASHION)
                .build();

        Product saved = productRepository.save(product);

        assertNotNull(saved);
    }

    @Test
    @DisplayName("id 2번인 데이터 삭제")
    void testRemove() {
        //given
        long id = 2L;

        //when
        productRepository.deleteById(id);
        //then
    }

    @Test
    @DisplayName("상품 전체 조회시 상품 갯수가 4개")
    void testFindAll() {
        //given

        //when
        List<Product> products = productRepository.findAll();

        //then
        products.forEach(System.out::println);

        assertEquals(4,products.size());
    }

    @Test
    @DisplayName("3번 상품 조회->상품명 구두")
    void testFIndOne() {
        //given
        long id = 3L;
        //when
        Optional<Product> product = productRepository.findById(id);
        //then
       product.ifPresent(p -> {
           assertEquals("구두",p.getName());
       });

       Product foundProduct = product.get();
       assertNotNull(foundProduct);
    }
}