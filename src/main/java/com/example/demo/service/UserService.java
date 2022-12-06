package com.example.demo.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    HashMap<String, Object> selectByUsername(String username);

    HashMap<String, Object> selectByUserId(int userId);

    int insertIntoUser( String username, String password, int faceId, int anonymous);

    int insertIntoRoom(int hostId);

    ObjectNode selectRoomById(int roomId);

    void enterRoom(int guestId, int roomId);

    void quitRoom(int roomId);
    void finishBattle(int roomId);
    void updateHostScore(int curScore, int roomId);

    void updateGuestScore(int roomId, int score);

    List<HashMap<String, Object>> getRank();

    void endGame(int roomId);
    void updateWinCnt( int userId);
    void updateUserPswById(int userId, String psw);

    void updateFaceId(int faceId, int userId);

    void updateUsername(int userId, String username);
    void updateScoreById(int newScore, int userId);

    int getRankOfUserById(int userId);
}
