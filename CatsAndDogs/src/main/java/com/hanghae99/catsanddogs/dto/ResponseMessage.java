package com.hanghae99.catsanddogs.dto;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;

}
