package com.hanghae99.catsanddogs.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;

}
