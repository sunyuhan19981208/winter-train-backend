package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

public interface QuestionService {
    List<HashMap<String,Object>>getQuestionList(int roomId);
    void insertQuestionsInRoom(int roomId);
}
