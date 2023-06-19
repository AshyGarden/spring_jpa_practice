package com.spring.jpa.chap05_practice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PageResponseDTO {
    //이 정보들이 pageInfo가 됨.
    private int startPage;
    private  int endPage;
    private int currentPage;

    private  boolean prev;
    private  boolean next;

    private int totalCount;
}
