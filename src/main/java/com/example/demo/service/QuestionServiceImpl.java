package com.example.demo.service;

import com.example.demo.entity.Question;
import com.example.demo.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestionList(int roomId) {
        String qs = questionMapper.getQuestionsByRoomId(roomId);
        if (qs == null) {
            return Collections.EMPTY_LIST;
        }
        String[] stringList = qs.split(",");

        return Arrays.stream(stringList)
                .mapToInt(Integer::parseInt)
                .mapToObj(questionMapper::selectQuestionById)
                .collect(Collectors.toList());
    }

    @Override
    public void insertQuestionsInRoom(int roomId) {
        int amount = 10;
        Set<Question> set = questionMapper.randomQuestions(amount);

        StringBuilder res = new StringBuilder();
        for (Question q : set) {
            int qid = q.getQid();
            res.append(qid).append(",");
        }

        res = new StringBuilder(res.substring(0, res.length() - 1));
        System.out.println(res);
        questionMapper.insertQuestionsInRoom(roomId, res.toString());
    }
}
