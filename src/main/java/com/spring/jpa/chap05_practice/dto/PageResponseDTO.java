package com.spring.jpa.chap05_practice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter @Setter @ToString
public class PageResponseDTO<T> {
    //이 정보들이 pageInfo가 됨.
    private int startPage;
    private  int endPage;
    private int currentPage;

    private  boolean prev;
    private  boolean next;

    private int totalCount;

    //한 페이지에 배치할 페이지 버튼수
    private static final int pageCount = 10;

    //제네릭 타입을 <T>로 선언하면, 전달되는 generic 타입에 따라
    //T가 결정된다. class에도 제네릭 타입<T>를 선언필요!
    public PageResponseDTO (Page<T> pageData) {
        this.totalCount = (int) pageData.getTotalElements();
        this.currentPage = pageData.getPageable().getPageNumber() + 1;
        this.endPage = (int)Math.ceil((double) currentPage/pageCount)*pageCount;
        this.startPage = endPage - pageCount + 1;

        int realEnd = pageData.getTotalPages();

        if(realEnd < this.endPage) this.endPage = realEnd;

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }
}
