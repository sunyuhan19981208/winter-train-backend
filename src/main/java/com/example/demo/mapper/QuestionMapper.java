package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Repository
public interface QuestionMapper {
    @Select("select count(1) from question")
    int getSize();
    @Select("select * from question where qid = #{qid}")
    HashMap<String,Object>selectQuestionById(@Param("qid") int qid);
    @Insert("insert into q_in_room(roomId,questions) values(#{roomId},#{questions})")
    void insertQuestionsInRoom(@Param("roomId")int roomId,@Param("questions")String questions);
    @Select("select questions from q_in_room where roomId=#{roomId}")
    String getQuestionsByRoomId(@Param("roomId")int roomId);
}
