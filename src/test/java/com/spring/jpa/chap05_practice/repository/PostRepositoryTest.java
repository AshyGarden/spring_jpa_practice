package com.spring.jpa.chap05_practice.repository;

import com.spring.jpa.chap05_practice.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Test
    @DisplayName("bulk test")
    void bulkInsert() {
       for(int i = 1; i <= 378; i++){
           postRepository.save(
                   Post.builder()
                           .title("title"+i)
                           .content("content"+i)
                           .writer("writer"+i)
                           .build()
           );
        }
    }



}
