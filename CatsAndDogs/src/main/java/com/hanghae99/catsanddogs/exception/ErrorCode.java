package com.hanghae99.catsanddogs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER("파라미터 값을 확인해주세요.", 400),
    INVALID_USERNAME_PATTERN("id는 소문자와 숫자 조합 4자리에서 10자리입니다.",400),
    INVALID_PASSWORD_PATTERN("비밀번호는 소문자, 대문자, 숫자, 특수문자(!@#$%^&+=) 조합 8자리에서 15자리입니다.",400),
    DUPLICATE_USERNAME("중복된 아이디가 존재합니다.", 400),
    REQUIRED_ALL("모든 항목이 필수값입니다.",400),
    //404 NOT_FOUND 잘못된 리소스 접근
    CONTENT_NOT_FOUND("존재하지 않는 게시글 입니다.",404),
    USERNAME_NOT_FOUND("존재하지 않는 아이디 입니다.",404),
    INCORRECT_PASSWORD("잘못된 비밀번호입니다.",404),
    AUTHORIZATION_DELETE_FAIL("삭제 권한이 없습니다.", 401 ),
    AUTHORIZATION_UPDATE_FAIL("수정 권한이 없습니다.", 401),

    INTERNAL_SERVER_ERROR("서버 에러입니다. 서버 팀에 연락주세요!", 500);


    private final String msg;
    private final int statusCode;
}

/*
    만들고 싶은 에러가 있다면 위처럼 적어준 후
    괄호 안에 해당 에러가 터졌을 때 반환해주고 싶은 메세지와 상태 코드 적어주기.

    그 후에 service에서 해당 에러를 호출하면 자동으로 클라이언트와 msg, statusCode, "error" 반환

    코드 예시 =>
    if (글쓴이 != 로그인한 유저) { //삭제 권한 없음
            throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
    }

    Post post = postRepository.findById(id).orElseThrow( //해당 게시글 없음
                () ->  new CustomException(ErrorCode.CONTENT_NOT_FOUND)
    );
    등등 편한데로 쓰면 됨.
*/
