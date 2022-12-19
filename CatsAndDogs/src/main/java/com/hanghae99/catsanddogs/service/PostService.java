package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.post.PostRequestDto;
import com.hanghae99.catsanddogs.dto.post.PostResponseDto;
import com.hanghae99.catsanddogs.dto.post.PostResponseListDto;
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
import java.util.List;
import java.util.UUID;

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

    public PostResponseDto createPost(PostRequestDto requestDto, MultipartFile file, User user) throws Exception {

        if(file.getContentType() == null || !file.getContentType().startsWith("image"))
            throw new CustomException(ErrorCode.WRONG_IMAGE_FORMAT);



        String picturePath = System.getProperty("user.dir") + "\\CatsAndDogs\\src\\main\\resources\\static\\img"; //파일 저장 경로 지정
        UUID uuid = UUID.randomUUID(); // 파일 이름에 붙일 랜덤 식별자
        String pictureName = uuid + "_" + file.getOriginalFilename(); // 새로운 이름 - 이름이 같으면 오류나서 이렇게 해줌
        File saveFile = new File(picturePath, pictureName);
        file.transferTo(saveFile); // 업로드 한 파일 데이터를 지정한 파일에 저장

        System.out.println("user.getNickname() = " + user.getNickname());

        Post post = postRepository.save(new Post(requestDto, picturePath, pictureName, user.getNickname()));

        return new PostResponseDto(post);


    }


//    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, MultipartFile file) throws Exception {
//
//        String picturePath = System.getProperty("user.dir") + "\\CatsAndDogs\\src\\main\\resources\\static\\img"; //파일 저장 경로 지정
//        UUID uuid = UUID.randomUUID(); // 파일 이름에 붙일 랜덤 식별자
//        String pictureName = uuid + "_" + file.getOriginalFilename(); // 새로운 이름 - 이름이 같으면 오류나서 이렇게 해줌
//        File saveFile = new File(picturePath, pictureName);
//        file.transferTo(saveFile); // 업로드 한 파일 데이터를 지정한 파일에 저장
//
//        Post post = postRepository.findByIdAndUsername(postId).orElseThrow(
//                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
//        );
//        post.update(requestDto, picturePath, pictureName);
//
//        return new PostResponseDto(post);
//
//
//    }
}
