package com.test.testserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage<T> {
    private String msg;
    private int statusCode;
    private T data;
}
