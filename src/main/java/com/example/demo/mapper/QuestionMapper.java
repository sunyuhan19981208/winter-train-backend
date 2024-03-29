package com.example.demo.mapper;

import com.example.demo.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionMapper {
    @Select("select count(1) from question")
    int getSize();

    @Select("select * from question where qid = #{qid}")
    Question selectQuestionById(@Param("qid") int qid);

    @Insert("insert into q_in_room(roomId,questions) values(#{roomId},#{questions})")
    void insertQuestionsInRoom(@Param("roomId") int roomId, @Param("questions") String questions);

    @Select("select questions from q_in_room where roomId=#{roomId}")
    String getQuestionsByRoomId(@Param("roomId") int roomId);

    @Select("select * from question order by RAND() limit #{amount}")
    Set<Question> randomQuestions(@Param("amount") int amount);
}
