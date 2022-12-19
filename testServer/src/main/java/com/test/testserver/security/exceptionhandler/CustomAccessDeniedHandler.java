package com.test.testserver.security.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.testserver.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private final ObjectMapper om;

    private static final String CUSTOM_DEFAULT_ERROR_MSG = "권한이 없습니다.";

    private static final String DEFAULT_ERROR_MSG = "접근이 거부되었습니다.";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String errorMsg = accessDeniedException.getMessage().equals(DEFAULT_ERROR_MSG)
                ? CUSTOM_DEFAULT_ERROR_MSG
                : accessDeniedException.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMsg);

        String result = om.writeValueAsString(errorResponse);

        response.getWriter().write(result);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
