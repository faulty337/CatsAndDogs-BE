package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
import com.hanghae99.catsanddogs.dto.post.*;
import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.entity.User;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional(readOnly = true)
    public MainResponseDto getPostList(Long userId) {

        List<Post> postList = postRepository.findAll(); //게시물이 많아지면 문제가 될듯
        PostResponseListDto postResponseListDto = new PostResponseListDto();
        for(Post post : postList){
            postResponseListDto.addPostResponseDto(new PostResponseDto(post, userId));
        }
        List<PostResponseDto> rank = postRepository.findTop2ByCreatedAtAfterOrderByLikeCountDesc(LocalDateTime.now().minusDays(1)).stream().map(post -> new PostResponseDto(post, userId)).collect(Collectors.toList());
        MainResponseDto mainResponseDto = new MainResponseDto(postResponseListDto, rank);

        LocalDateTime.now().minusDays(1);
        return mainResponseDto;
    }

    @Transactional(readOnly = true)
    public DetailResponseDto getPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        PostResponseDto postResponseDto = new PostResponseDto(post, userId);
        List<CommentResponseDto> commentResponseDtoList = post.getCommentList().stream().map(comment -> new CommentResponseDto(comment, userId)).toList();
        return new DetailResponseDto(postResponseDto, commentResponseDtoList);
    }


    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) throws Exception {



        System.out.println("user.getNickname() = " + user.getNickname());

        Post post = postRepository.save(new Post(requestDto, "picturePath", "pictureName", user.getNickname()));

        return new PostResponseDto(post);


    }


    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) throws Exception {

        Post post = postRepository.findByIdAndNickname(postId, user.getNickname()).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        post.update(requestDto, "picturePath", "pictureName");

        return new PostResponseDto(post);


    }
}
