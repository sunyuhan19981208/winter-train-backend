package com.example.demo.service;

import com.example.demo.entity.Question;
import com.example.demo.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestionList(int roomId) {
        String[] stringList = questionMapper.getQuestionsByRoomId(roomId).split(",");
        List<Integer> li = new LinkedList<>();
        for (String it : stringList) li.add(Integer.parseInt(it));
        List<Question> res = new LinkedList<>();
        for (Integer qid : li) {
            res.add(questionMapper.selectQuestionById(qid));
        }
        return res;
    }

    @Override
    public void insertQuestionsInRoom(int roomId) {
        int qSize = questionMapper.getSize();
        int min = 1;
        List<Integer> set = new ArrayList<>(10);
        Random random = new Random(System.currentTimeMillis());

        while (set.size() < 10) {
            int i = random.nextInt(qSize) + 1;
            if (!set.contains(i)) {
                set.add(i);
            }
        }

        StringBuilder res = new StringBuilder();
        for (Integer qid : set) {
            res.append(qid).append(",");
        }

        res = new StringBuilder(res.substring(0, res.length() - 1));
        System.out.println(res);
        questionMapper.insertQuestionsInRoom(roomId, res.toString());
    }
}
