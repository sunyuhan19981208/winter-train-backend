package com.example.demo.controller;

import com.example.demo.entity.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping(value = "getQuestions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> getQuestions(@RequestParam("roomId") int roomId) {

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
