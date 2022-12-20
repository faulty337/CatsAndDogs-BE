package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
import com.hanghae99.catsanddogs.dto.post.*;
import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.entity.User;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.PostRepository;
import com.hanghae99.catsanddogs.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private S3Uploader s3Uploader;


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
    public PostResponseDto createPost(PostRequestDto requestDto, MultipartFile image, User user) throws Exception {

        if(image.getContentType() == null || !image.getContentType().startsWith("image"))
            throw new CustomException(ErrorCode.WRONG_IMAGE_FORMAT);

        UUID uuid = UUID.randomUUID(); // 파일 이름에 붙일 랜덤 식별자
        String pictureName = uuid + "_" + image.getOriginalFilename(); // 새로운 이름 - 이름이 같으면 오류나서 이렇게 해줌

        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            requestDto.setPicturePath(storedFileName);
        }

        Post post = postRepository.save(new Post(requestDto, user, requestDto.getPicturePath(), pictureName));

        return new PostResponseDto(post);


    }


    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, MultipartFile image, User user) throws Exception {

        if(!image.getContentType().startsWith("image"))
            throw new CustomException(ErrorCode.WRONG_IMAGE_FORMAT);

        UUID uuid = UUID.randomUUID(); // 파일 이름에 붙일 랜덤 식별자
        String pictureName = uuid + "_" + image.getOriginalFilename(); // 새로운 이름 - 이름이 같으면 오류나서 이렇게 해줌

        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            requestDto.setPicturePath(storedFileName);
        }

//        Post post = postRepository.findByIdAndUsers(postId, user).orElseThrow(
//                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
//        );

        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        if(!post.getUsers().getId().equals(user.getId())){
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }
        post.update(requestDto, requestDto.picturePath, pictureName);

        return new PostResponseDto(post);


    }
}