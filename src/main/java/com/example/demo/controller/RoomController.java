package com.example.demo.controller;

import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin
public class RoomController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "createRoom", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> createRoom(@Param("hostId") int hostId) {
        int roomId = userService.insertIntoRoom(hostId);
        questionService.insertQuestionsInRoom(roomId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("roomId", roomId);
            }
        };
    }

    @RequestMapping(value = "enterRoom", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> enterRoom(@Param("roomId") int roomId, @Param("guestId") int guestId) {
        HashMap<String, Object> room = userService.selectRoomById(roomId);
        if (room == null) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该房间不存在");
                }
            };
        } else if (((Number) room.get("roomStatus")).intValue() == -1) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该房间已失效");
                }
            };
        } else if (room.get("guestId") != null||room.get("hostId")==null) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该房间已满园");
                }
            };
        } else {
            userService.enterRoom(guestId, roomId);
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "进入房间成功");
                }
            };
        }
    }

    @RequestMapping(value = "quitRoom", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> quitRoom(@Param("roomId") int roomId) {
        userService.quitRoom(roomId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("msg", "退出房间成功");
            }
        };
    }

    @RequestMapping(value = "queryRoom", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> queryRoom(@Param("roomId") int roomId) {
        HashMap<String, Object> room = userService.selectRoomById(roomId);

        HashMap<String, Object> host = userService.selectByUserId(((Number) room.getOrDefault("hostId", -1)).intValue());
        HashMap<String, Object> guest = userService.selectByUserId(((Number) room.getOrDefault("guestId", -1)).intValue());
        if (host != null) {
            host.remove("password");
        }
        if (guest != null) {
            guest.remove("password");
        }

        room.put("hostInfo", host);
        room.put("guestInfo", guest);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("data", room);
            }
        };
    }

    @RequestMapping(value = "updateScore", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> updateScore(@Param("roomId") int roomId, @Param("userId") int userId, @Param("curScore") int curScore) {
        HashMap<String, Object> room = userService.selectRoomById(roomId);
        if ((int) room.get("roomStatus") == 2) return new HashMap<String, Object>() {
            {
                put("respCode", 2);
                put("msg", "游戏已结束");
            }
        };
        if (((int) room.get("hostId")) == (userId)) {
            userService.updateHostScore(curScore, roomId);
        } else {
            userService.updateGuestScore(curScore, roomId);
        }
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("msg", "更新分数成功");
            }
        };
    }

    @RequestMapping(value = "endGame", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> endGame(@Param("roomId") int roomId) {
        userService.endGame(roomId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("msg", "游戏结束");
            }
        };
    }
}
