package com.spring.jpa.chap05_practice.dto;

import lombok.*;

import java.util.List;
@Getter @Setter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PostListResponseDTO {
    private  int count; // 총 게시물 수
    private PageResponseDTO pageInfo; //페이지 랜더링 정보
    private List<PostDetailResponseDTO> posts; //게시물 랜더링 정보
}

