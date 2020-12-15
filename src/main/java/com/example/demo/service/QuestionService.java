package com.example.demo.service;


import com.example.demo.bean.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionList(int roomId);
    void insertQuestionsInRoom(int roomId);
}
