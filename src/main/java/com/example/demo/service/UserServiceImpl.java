package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public HashMap<String, Object> selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public HashMap<String, Object> selectByUserId(int userId) {
        HashMap<String, Object> ret = userMapper.selectByUserId(userId);
        if (ret != null) {
            ret.remove("password");
        }
        return ret;
    }

    @Override
    public int insertIntoUser(String username, String password, int faceId, int anonymous) {
        Integer userId = userMapper.getMaxUserId();
        if (userId == null) {
            userId = 1;
        } else {
            userId += 1;
        }

        userMapper.insertIntoUser(username, password, userId, faceId, anonymous);
        return userId;
    }

    @Override
    public int insertIntoRoom(int hostId) {
        Integer roomId = userMapper.getMaxRoomId();
        roomId = roomId == null ? 1 : roomId + 1;
        userMapper.insertIntoRoom(roomId, hostId);
        return roomId;
    }

    @Override
    public ObjectNode selectRoomById(int roomId) {
        HashMap<String, Object> room = userMapper.selectRoomById(roomId);
        return new ObjectMapper().valueToTree(room);
    }

    @Override
    public void enterRoom(int guestId, int roomId) {
        userMapper.updateRoom(roomId, guestId);
    }

    @Override
    public void quitRoom(int roomId) {
        userMapper.quitRoom(roomId);
    }

    @Override
    public void finishBattle(int roomId) {
        userMapper.finishBattle(roomId);
    }

    @Override
    public void updateHostScore(int curScore, int roomId) {
        userMapper.updateHostScore(curScore, roomId);
    }

    @Override
    public void updateScoreById(int newScore, int userId) {
        userMapper.updateScoreById(newScore, userId);
    }

    @Override
    public void updateGuestScore(int roomId, int score) {
        userMapper.updateGuestScore(roomId, score);
    }

    @Override
    public List<HashMap<String, Object>> getRank() {

        return userMapper.getRank();
    }

    @Override
    public void endGame(int roomId) {
        HashMap<String, Object> map = userMapper.selectRoomById(roomId);
        ObjectNode room = new ObjectMapper().valueToTree(map);
        int hostId = room.get("hostId").asInt();
        int guestId = room.get("guestId").asInt();
        int hostScore = room.get("hostScore").asInt();
        int guestScore = room.get("guestScore").asInt();
        userMapper.updateUserScore(hostId, hostScore);
        userMapper.updateUserScore(guestId, guestScore);
        if (hostScore > guestScore) userMapper.updateWinCnt(hostId);
        else if (hostScore < guestScore) userMapper.updateWinCnt(guestId);
        userMapper.endGame(roomId);
    }

    @Override
    public void updateWinCnt(int userId) {
        userMapper.updateWinCnt(userId);
    }

    @Override
    public void updateUserPswById(int userId, String psw) {
        userMapper.updateUserPswById(userId, psw);
    }

    @Override
    public void updateFaceId(int faceId, int userId) {
        userMapper.updateFaceId(faceId, userId);
    }

    @Override
    public void updateUsername(int userId, String username) {
        userMapper.updateUsername(userId, username);
    }

    @Override
    public int getRankOfUserById(int userId) {
        return userMapper.getRankOfUserById(userId);
    }
}
