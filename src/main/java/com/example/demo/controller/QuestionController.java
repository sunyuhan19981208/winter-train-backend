package com.example.demo.controller;

import com.example.demo.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "getQuestions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> getQuestions(@Param("roomId") int roomId) {
        List<HashMap<String, Object>> questionList = questionService.getQuestionList(roomId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("data", questionList);
            }
        };
    }
}
