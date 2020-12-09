package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    HashMap<String,Object>selectByUsername(String username);
    public HashMap<String, Object> selectByUserId(int userId);
    void insertIntoUser(HashMap<String,Object>user);
    int insertIntoRoom(int hostId);
    HashMap<String,Object>selectRoomById(int roomId);
    void enterRoom(int guestId,int roomId);
    void quitRoom(int roomId);
    void updateHostScore(int roomId,int score);
    void updateGuestScore(int roomId,int score);
    List<HashMap<String,Object>>getRank();
    void endGame(int roomId);
    void updateUserPswById(int userId,String psw);
    void updateFaceId(int faceId,int userId);
    void updateUsername(int userId,String username);

}
