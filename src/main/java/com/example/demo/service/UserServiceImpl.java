package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public HashMap<String, Object> selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public HashMap<String, Object> selectByUserId(int userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public int insertIntoUser(HashMap<String, Object> user) {
        Integer maxId=userMapper.getMaxUserId();
        int userId=1;
        if(maxId!=null)userId=maxId+1;
        userMapper.insertIntoUser((String)user.get("username"),(String)user.get("password"),userId,(int)user.get("faceId"));
        return userId;
    }

    @Override
    public int insertIntoRoom(int hostId) {
        Integer maxRoomId=userMapper.getMaxRoomId();
        int roomId=1;
        if(maxRoomId!=null)roomId=maxRoomId+1;
        userMapper.insertIntoRoom(roomId,hostId);
        return roomId;
    }

    @Override
    public HashMap<String, Object> selectRoomById(int roomId) {
        return userMapper.selectRoomById(roomId);
    }

    @Override
    public void enterRoom(int guestId, int roomId) {
        userMapper.updateRoom(roomId,guestId);
    }

    @Override
    public void quitRoom(int roomId) {
        userMapper.quitRoom(roomId);
    }

    @Override
    public void updateHostScore(int curScore,int roomId) {
        userMapper.updateHostScore(curScore,roomId);
    }

    @Override
    public void updateGuestScore(int roomId, int score) {
        userMapper.updateGuestScore(roomId,score);
    }

    @Override
    public List<HashMap<String, Object>> getRank() {

        return userMapper.getRank();
    }

    @Override
    public void endGame(int roomId) {
        HashMap<String,Object>room=userMapper.selectRoomById(roomId);
        int hostId=(int)room.get("hostId");
        int guestId=(int)room.get("guestId");
        int hostScore=(int)room.get("hostScore");
        int guestScore=(int)room.get("guestScore");
        userMapper.updateUserScore(hostId,hostScore);
        userMapper.updateUserScore(guestId,guestScore);
        if(hostScore>guestScore)userMapper.updateWinCnt(hostId);
        else if(hostScore<guestScore)userMapper.updateWinCnt(guestId);
        userMapper.endGame(roomId);
    }

    @Override
    public void updateUserPswById(int userId,String psw) {
        userMapper.updateUserPswById(userId,psw);
    }

    @Override
    public void updateFaceId(int faceId, int userId) {
        userMapper.updateFaceId(faceId,userId);
    }

    @Override
    public void updateUsername(int userId, String username) {
        userMapper.updateUsername(userId,username);
    }
}
