package com.hanghae99.catsanddogs.exception;

import com.hanghae99.catsanddogs.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //컨트롤러에서 에러가 터지는 것을 감지
public class GlobalExceptionHandler{

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex){
        System.out.println(ex.getMessage());
        return new ResponseEntity( new ResponseMessage( ex.getErrorCode().getMsg(), ex.getErrorCode().getStatusCode(), ex.getErrorCode() )
                                    , HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleServerException(Exception ex){
        return new ResponseEntity(new ResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "error")
                                    , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
