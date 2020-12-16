package com.example.demo.controller.battle;

import java.util.HashMap;
import java.util.Random;

public class Battle {
    private int id = -1;
    private int creatorId = -1;
    private int guestId = -1;
    private long creatorCredits = 0;
    private long guestCredits = 0;
    private int creatorQuestionIndex = 0;
    private int guestQuestionIndex = 0;

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    private int roomStatus = 1;

    @Override
    public String toString() {
        return "Battle{" +
                "id='" + id + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", guestId='" + guestId + '\'' +
                ", creatorCredits=" + creatorCredits +
                ", creatorQuestionIndex=" + creatorQuestionIndex +
                ", guestQuestionIndex=" + guestQuestionIndex +
                ", guestCredits=" + guestCredits +
                '}';
    }


    public void increaseGuestQuestionIndex() {
        guestQuestionIndex += 1;
    }

    public void increaseCreatorQuestionIndex() {
        creatorQuestionIndex += 1;
    }

    public int getCreatorQuestionIndex() {
        return creatorQuestionIndex;
    }

    public void setCreatorQuestionIndex(int creatorQuestionIndex) {
        this.creatorQuestionIndex = creatorQuestionIndex;
    }

    public int getGuestQuestionIndex() {
        return guestQuestionIndex;
    }

    public void setGuestQuestionIndex(int guestQuestionIndex) {
        this.guestQuestionIndex = guestQuestionIndex;
    }


    public long getCreatorCredits() {
        return creatorCredits;
    }

    public void setCreatorCredits(long creatorCredits) {
        this.creatorCredits = creatorCredits;
    }

    public long getGuestCredits() {
        return guestCredits;
    }

    public void setGuestCredits(long guestCredits) {
        this.guestCredits = guestCredits;
    }


    public Battle() {

    }

    public Battle(int creatorId) {
        this.creatorId = creatorId;
        this.id = (int) (System.currentTimeMillis() + new Random().nextInt(1000));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public static Battle fromRoom(HashMap<String, Object> room) {
        Battle battle = new Battle();
        battle.setId(((Number) room.getOrDefault("roomId", 0)).intValue());
        battle.setCreatorId(((Number) room.getOrDefault("hostId", -1)).intValue());
        battle.setCreatorCredits(((Number) room.getOrDefault("hostScore", 0)).intValue());
        battle.setCreatorQuestionIndex(((Number) room.getOrDefault("hostIndex", 0)).intValue());

        battle.setGuestId(((Number) room.getOrDefault("guestId", -1)).intValue());
        battle.setGuestCredits(((Number) room.getOrDefault("guestScore", 0)).intValue());
        battle.setGuestQuestionIndex(((Number) room.getOrDefault("guestIndex", 0)).intValue());
        battle.setRoomStatus(((Number)room.getOrDefault("roomStatus", 1)).intValue());

        return battle;
    }


}
