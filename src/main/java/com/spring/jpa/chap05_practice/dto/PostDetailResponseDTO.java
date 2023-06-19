package com.spring.jpa.chap05_practice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jpa.chap05_practice.entity.HashTag;
import com.spring.jpa.chap05_practice.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDetailResponseDTO {

    private String author;
    private String title;
    private String content;
    private List<String> hashTags;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;

    //Entity를 DTO로 변환하는 생성자
    public PostDetailResponseDTO(Post post) { //상세내용 응답 객체
        this.author = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getCreateDate();

        //hashtag의 경우 가공해서 집어넣어야한다.(list 타입이 서로다름)
//        List<String> list = new ArrayList<>();
//        for(HashTag ht : post.getHashTags()){
//            list.add(ht.getTagName());
//        }
//        this.hashTags = list;

        //위와 같음
        this.hashTags = post.getHashTags()//List<HashTag>
                .stream()//stream 객체를 받아옴(collection 데이터를 함수선언 형식으로 처리할수 있게 해주는 객체)
                .map(HashTag::getTagName) // Stream 내의 요소들에 대해 함수가 적용된 결과를 새로운 요소로 mapping
                .collect(Collectors.toList()); //Stream 객체를 새로운 리스트로 리턴 ->this.hashTags에 대입
    }
}
