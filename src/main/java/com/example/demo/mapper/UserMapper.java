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
    @Select("select * from user order by score desc")
    List<HashMap<String,Object>>getRank();
    @Select("select * from user where username= #{username}")
    HashMap<String,Object>selectByUsername(@Param("username")String username);
    @Select("insert into user(username,userId,password,score,faceId) values(#{username},#{userId},#{password},0,#{faceId})")
    void insertIntoUser(@Param("username")String username,@Param("password")String password,
                                         @Param("userId")int userId,@Param("faceId")int faceId);
    @Select("select max(userId) from user")
    int getMaxUserId();
    @Select("select max(roomId) from room")
    int getMaxRoomId();
    @Insert("insert into room(roomId,hostId)values(#{roomId},#{hostId})")
    void insertIntoRoom(@Param("roomId")int roomId,@Param("hostId")int hostId);
    @Update("update room set guestId=#{guestId},guestStatus=1 where roomId=#{roomId}")
    void updateRoom(@Param("roomId")int roomId,@Param("guestId")int guestId);
    @Update("update room set roomStatus=-1 where roomId=#{roomId}")
    void quitRoom(@Param("roomId")int roomId);
    @Select("select * from room where roomId=#{roomId}")
    HashMap<String,Object> selectRoomById(@Param("roomId")int roomId);
    @Update("update room set hostScore=#{score},hostIndex=1+hostIndex where roomId=#{roomId}")
    void updateHostScore(@Param("score")int score,@Param("roomId")int roomId);
    @Update("update room set guestScore=#{score},guestIndex=1+guestIndex where roomId=#{roomId}")
    void updateGuestScore(@Param("score")int score,@Param("roomId")int roomId);
    @Update("update room set roomStatus=2 where roomId=#{roomId}")
    void endGame(@Param("roomId")int roomId);
    @Update("update user set password=#{password}where userId=#{userId}")
    void updateUserPswById(@Param("userId")int userId,@Param("password")String password);
    @Update("update user set faceId=#{faceId} where userId=#{userId}")
    void updateFaceId(@Param("faceId")int faceId,@Param("userId")int userId);
    @Update("update user set score=#{score}+score where userId =#{userId}")
    void updateUserScore(@Param("userId")int userId,@Param("score")int score);
    @Update("update user set username=#{username} where userId=#{userId}")
    void updateUsername(@Param("userId")int userId,@Param("username")String username);
    @Update("update user set winCnt=winCnt+1 where userId=#{userId}")
    void updateWinCnt(@Param("userId")int userId);
}
