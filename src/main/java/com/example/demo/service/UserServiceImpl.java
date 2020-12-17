package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
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
        HashMap<String, Object> ret = userMapper.selectByUsername(username);
        ret.remove("password");
        return ret;
    }

    @Override
    public HashMap<String, Object> selectByUserId(int userId) {
        HashMap<String, Object> ret = userMapper.selectByUserId(userId);
        ret.remove("password");
        return ret;
    }

    @Override
    public int insertIntoUser(HashMap<String, Object> user) {
        Integer userId = userMapper.getMaxUserId();
        if (userId == null) {
            userId = 1;
        } else {
            userId += 1;
        }

        userMapper.insertIntoUser((String) user.get("username"), (String) user.get("password"), userId, (int) user.get("faceId"), (Integer) user.getOrDefault("anonymous", 0));
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
    public HashMap<String, Object> selectRoomById(int roomId) {
        return userMapper.selectRoomById(roomId);
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
        HashMap<String, Object> room = userMapper.selectRoomById(roomId);
        int hostId = (int) room.get("hostId");
        int guestId = (int) room.get("guestId");
        int hostScore = (int) room.get("hostScore");
        int guestScore = (int) room.get("guestScore");
        userMapper.updateUserScore(hostId, hostScore);
        userMapper.updateUserScore(guestId, guestScore);
        if (hostScore > guestScore) userMapper.updateWinCnt(hostId);
        else if (hostScore < guestScore) userMapper.updateWinCnt(guestId);
        userMapper.endGame(roomId);
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
