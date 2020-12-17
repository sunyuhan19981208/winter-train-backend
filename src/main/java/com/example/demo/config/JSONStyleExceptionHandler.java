package com.example.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

@ControllerAdvice
public class JSONStyleExceptionHandler {
    private static PrintStream errorLog = null;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public HashMap<String, Object> handleAnyException(HttpServletRequest request, HttpServletResponse response,
                                                      Exception e) {
        e.printStackTrace();
        try {
            if (errorLog == null) {
                errorLog = new PrintStream(new FileOutputStream(new File("error.log"), true));
            }
            e.printStackTrace(errorLog);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        response.setStatus(500);
        return new HashMap<String, Object>() {
            {
                put("error", true);
                put("respCode", 500);
                put("request path", request.getServletPath());
                put("exception message", e.getMessage());
                put("exception type", e.getClass().toGenericString());

            }
        };
    }
}
