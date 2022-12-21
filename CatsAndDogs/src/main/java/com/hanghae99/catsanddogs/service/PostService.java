package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
import com.hanghae99.catsanddogs.dto.post.*;
import com.hanghae99.catsanddogs.entity.Comment;
import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.entity.User;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.CommentRepository;
import com.hanghae99.catsanddogs.repository.LikeCommentRepository;
import com.hanghae99.catsanddogs.repository.LikePostRepository;
import com.hanghae99.catsanddogs.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;

    private final LikeCommentRepository likeCommentRepository;


    @Transactional(readOnly = true)
    public MainResponseDto getPostList(Long userId) {

        List<Post> postList = postRepository.findAll();
        PostResponseListDto postResponseListDto = new PostResponseListDto();
        for(Post post : postList){
            postResponseListDto.addPostResponseDto(new PostResponseDto(post, userId));
        }
        List<PostResponseDto> rank = postRepository.findTop2ByCreatedAtAfterOrderByLikeCountDesc(LocalDateTime.now().minusDays(1)).stream().map(post -> new PostResponseDto(post, userId)).collect(Collectors.toList());
        return new MainResponseDto(postResponseListDto, rank);
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
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {


        Post post = postRepository.save(new Post(requestDto, user));

        return new PostResponseDto(post);


    }


    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user)  {


        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        if(!post.getUsers().getId().equals(user.getId())){
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }
        post.update(requestDto);

        return new PostResponseDto(post);
    }


    @Transactional
    public void deletePost(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        if(!post.getUsers().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
        }
        List<Long> commentIdList = new ArrayList<>();

        for(Comment comment : post.getCommentList()){
            commentIdList.add(comment.getId());
        }

        if(!commentIdList.isEmpty()){
            likeCommentRepository.deleteAllByCommentIdIn(commentIdList);
            commentRepository.deleteAllByIdIn(commentIdList);
        }

        likePostRepository.deleteAllByPostId(postId);
        postRepository.delete(post);


    }
}