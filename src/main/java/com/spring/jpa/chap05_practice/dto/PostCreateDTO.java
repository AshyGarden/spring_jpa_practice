package com.spring.jpa.chap05_practice.dto;


import com.spring.jpa.chap05_practice.entity.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDTO {

    @NotBlank //공백 허용X
    @Size(min = 2, max = 5)
    private  String writer;

//    @NotNull(Null 허용x, ""," "는 허용!)
//    @NotEmpty(Null ,"" 허용x," "는 허용!)
    @NotBlank //공백+null 허용X
    @Size(min = 1, max = 20)
    private String title;

    private String content;

    private List<String> hashTags;

    public Post toEntity(){
        return  Post.builder()
                .writer(this.writer)
                .content(this.content)
                .title(this.title)
                .build(); //hashtag는 여기서 주입하는것이 아님!
    }

}
