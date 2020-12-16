package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserMapper {
    @Select("select count(0)+1 as 'rank'from user where score > ( select score from user where userId= #{userId} )")
    int getRankOfUserById(@Param("userId") int userId);

    @Select("select userId,username,score,faceId,winCnt from user where anonymous = 0 order by score desc limit 10")
    List<HashMap<String, Object>> getRank();

    @Select("select * from user where username= #{username}")
    HashMap<String, Object> selectByUsername(@Param("username") String username);

    @Select("select * from user where userId= #{userId}")
    HashMap<String, Object> selectByUserId(@Param("userId") int userId);

    @Select("insert into user(username,userId,password,score,faceId,anonymous) values(#{username},#{userId},#{password},0,#{faceId},#{anonymous})")
    void insertIntoUser(@Param("username") String username, @Param("password") String password,
                        @Param("userId") int userId, @Param("faceId") int faceId,@Param("anonymous")int anonymous);

    @Select("select max(userId) from user")
    Integer getMaxUserId();

    @Select("select max(roomId) from room")
    Integer getMaxRoomId();

    @Insert("insert into room(roomId,hostId)values(#{roomId},#{hostId})")
    void insertIntoRoom(@Param("roomId") int roomId, @Param("hostId") int hostId);

    @Update("update room set guestId=#{guestId},guestStatus=1 where roomId=#{roomId}")
    void updateRoom(@Param("roomId") int roomId, @Param("guestId") int guestId);

    @Update("update room set roomStatus=-1 where roomId=#{roomId}")
    void quitRoom(@Param("roomId") int roomId);

    @Select("select * from room where roomId=#{roomId}")
    HashMap<String, Object> selectRoomById(@Param("roomId") int roomId);

    @Update("update room set hostScore=#{score},hostIndex=1+hostIndex where roomId=#{roomId}")
    void updateHostScore(@Param("score") int score, @Param("roomId") int roomId);

    @Update("update room set guestScore=#{score},guestIndex=1+guestIndex where roomId=#{roomId}")
    void updateGuestScore(@Param("score") int score, @Param("roomId") int roomId);

    @Update("update room set roomStatus=2 where roomId=#{roomId}")
    void endGame(@Param("roomId") int roomId);

    @Update("update user set password=#{password}where userId=#{userId}")
    void updateUserPswById(@Param("userId") int userId, @Param("password") String password);

    @Update("update user set faceId=#{faceId} where userId=#{userId}")
    void updateFaceId(@Param("faceId") int faceId, @Param("userId") int userId);

    @Update("update user set score=#{score}+score where userId =#{userId}")
    void updateUserScore(@Param("userId") int userId, @Param("score") int score);

    @Update("update user set username=#{username} where userId=#{userId}")
    void updateUsername(@Param("userId") int userId, @Param("username") String username);

    @Update("update user set winCnt=winCnt+1 where userId=#{userId}")
    void updateWinCnt(@Param("userId") int userId);
}
