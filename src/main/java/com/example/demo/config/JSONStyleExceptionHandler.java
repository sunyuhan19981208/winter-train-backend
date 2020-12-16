package com.example.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class JSONStyleExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Map handleAnyException(HttpServletRequest request, Exception e) {
        return new HashMap<String, Object>() {
            {
                put("error", true);
                put("respCode", 500);
                put("request path", request.getServletPath());
                put("exception message",
                        e instanceof NoSuchElementException ? "Cannot find destination object in database for this request" : e.getMessage());

            }
        };
    }
}
