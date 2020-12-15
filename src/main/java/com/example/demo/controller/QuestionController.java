package com.example.demo.controller;

import com.example.demo.bean.Question;
import com.example.demo.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "getQuestions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> getQuestions(@Param("roomId") int roomId) {

        //TODO

        List<Question> questionList = questionService.getQuestionList(roomId);

        ArrayList<Object> prettyQuestionList = new ArrayList<>(questionList.size());
        for (Question question : questionList) {
            prettyQuestionList.add(question.toMap());
        }

        System.out.println(questionList);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("data", prettyQuestionList);
            }
        };
    }
}
