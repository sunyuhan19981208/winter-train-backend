package com.example.demo.service;

import com.example.demo.bean.Question;
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
        List<String>stringList=Arrays.asList(questionMapper.getQuestionsByRoomId(roomId).split(","));
        List<Integer>li=new LinkedList<>();
        for(String it:stringList)li.add(Integer.parseInt(it));
        List<Question>res=new LinkedList<>();
        for(Integer qid:li){
            res.add(questionMapper.selectQuestionById(qid));
        }
        return res;
    }

    @Override
    public void insertQuestionsInRoom(int roomId) {
        int qSize=questionMapper.getSize();
        int max=qSize;
        int min=1;
        Set<Integer> s=new HashSet<>();
        while(s.size()<10){
            int qid=(int) (Math.random()*(max-min)+min);
            if(!s.contains(qid))s.add(qid);
        }
        String res="";
        for(Integer qid:s)res+=qid+",";
        res=res.substring(0,res.length()-1);
        questionMapper.insertQuestionsInRoom(roomId,res);
    }
}
