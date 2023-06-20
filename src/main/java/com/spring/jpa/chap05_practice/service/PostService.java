package com.spring.jpa.chap05_practice.service;

import com.spring.jpa.chap05_practice.dto.*;
import com.spring.jpa.chap05_practice.entity.HashTag;
import com.spring.jpa.chap05_practice.entity.Post;
import com.spring.jpa.chap05_practice.repository.HashTagRepository;
import com.spring.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor //autowired 대신 쓰는 느낌
@Transactional // JPA 레파지토리는 트랜잭션 단위로 동작하기 때문에 작성해 주세요!
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    public PostListResponseDTO getPosts(PageDTO dto){

        //Pagealbe 객체 생성
        Pageable pageable = PageRequest.of(
                dto.getPage() -1,
                dto.getSize(),
                Sort.by("createDate").descending()
        );

        //DB에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);

        //게시물 정보만 꺼내기
        List<Post> postList = posts.getContent();

        //Entity 객체를 DTO객체로 변환한 리스트
        List<PostDetailResponseDTO> detailList
                =postList.stream()
                .map(post -> new PostDetailResponseDTO(post))
                .collect(Collectors.toList());;

        //DB에서 조회한 정보(Entity)를 JSON형태에 맞는 DTO로 변환(호출한 곳으로 return)
        return PostListResponseDTO.builder()
                .count(detailList.size()) //조회된 게시물 수
                .pageInfo(new PageResponseDTO(posts)) //생성자가
                .posts(detailList)
                .build();
    }

    public PostDetailResponseDTO getDetail(Long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException(id + "번 게시글이 존재하지 않습니다.")
        );

        return new PostDetailResponseDTO(postEntity);
    }

    public PostDetailResponseDTO insert(final PostCreateDTO dto)
            throws RuntimeException {

        //2개를 따로 저장해주어야함

        //게시물 저장
        Post saved = postRepository.save(dto.toEntity());

        //해시태그 저장
        List<String> hashTags = dto.getHashTags();
        if(hashTags != null && hashTags.size() > 0){
            hashTags.forEach(ht->{
                HashTag savedTag = hashTagRepository.save(
                        HashTag.builder()
                                .tagName(ht)
                                .post(saved)
                                .build()
                );
                //Post Entity는 DB에 save진행시 HashTag에 대한 내용을 갱신하지 않는다.
                //HashTag Entity는 따로 save진행
                //HashTag는 연관관계의 주인이기 떄문에 save진행시 Post를 전달하기때문에
                //DB와 Entity와의 상태가 동일하지만, Post는 HashTag의 정보가 비어있는 상태/
                //Post Entity에 연관관계 편의 매서드를 작성하여 HashTag의 내용을 동기화해야
                //추후에 진행되는 과정에서 문제가 발생하지 않습니다.(연관관게의 주인이 아니기 때문)
                saved.addHashTag(savedTag);
            });
        }

        return new PostDetailResponseDTO(saved);
    }


}
