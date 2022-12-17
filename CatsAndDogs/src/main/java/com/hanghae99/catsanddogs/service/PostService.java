package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.PostResponseDto;
import com.hanghae99.catsanddogs.dto.PostResponseListDto;
import com.hanghae99.catsanddogs.entity.CategoryEnum;
import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional(readOnly = true)
    public PostResponseListDto getPostList(Long userId) {

        List<Post> postList = postRepository.findAll(); //게시물이 많아지면 문제가 될듯
        PostResponseListDto postResponseListDto = new PostResponseListDto();
        for(Post post : postList){
            postResponseListDto.addPostResponseDto(new PostResponseDto(post, userId));
        }
        return postResponseListDto;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId, Long userId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        return new PostResponseDto(post, userId);
    }

//    public void test(){
//        for(int i = 0; i < 10; i++){
//            postRepository.save(new Post("제목"+i, "내용"+i,"Path", CategoryEnum.CAT));
//        }
//    }
}
