package com.example.demo.controller;

import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class RoomController {
    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    //    @GetMapping(
    //            value = "createRoom",
    //            produces = {MediaType.APPLICATION_JSON_VALUE})
    //    public HashMap<String, Object> createRoom(@RequestParam int hostId) {
    //        int roomId = userService.insertIntoRoom(hostId);
    //        questionService.insertQuestionsInRoom(roomId);
    //        return new HashMap<String, Object>() {
    //            {
    //                put("respCode", 1);
    //                put("roomId", roomId);
    //            }
    //        };
    //    }

    /**
     * 创建一个对战房间。初始化的房间状态是0，guestid是-1，
     * @param hostId
     * @return
     */
    @GetMapping(
            value = "createRoom",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> createRoom(@RequestParam int hostId) {

        System.out.println("creating room.");
        int roomId = userService.insertIntoRoom(hostId);
        questionService.insertQuestionsInRoom(roomId);

        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("roomId", roomId);
            }
        };
    }

    @RequestMapping(
            value = "enterRoom",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> enterRoom(@RequestParam("roomId") int roomId, @RequestParam("guestId") int guestId) {
        ObjectNode room = userService.selectRoomById(roomId);
        if (room == null) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该房间不存在");
                }
            };
        } else if ((room.get("roomStatus")).asInt() == -1) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该房间已失效");
                }
            };
        } else if (room.get("guestId").asInt() != -1 || room.get("hostId") == null) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该房间已满");
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

    @RequestMapping(
            value = "quitRoom",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> quitRoom(@RequestParam("roomId") int roomId) {
        userService.quitRoom(roomId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("msg", "退出房间成功");
            }
        };
    }

    @RequestMapping(
            value = "queryRoom",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> queryRoom(@RequestParam("roomId") int roomId) {
        ObjectNode room = userService.selectRoomById(roomId);

        HashMap<String, Object> host =
                userService.selectByUserId(room.get("hostId").asInt(-1));
        HashMap<String, Object> guest = userService.selectByUserId((room.get("guestId")).asInt(-1));
        if (host != null) {
            host.remove("password");
        }
        if (guest != null) {
            guest.remove("password");
        }

        room.putPOJO("hostInfo", host);
        room.putPOJO("guestInfo", guest);
        room.put("hostStatus", 1);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("data", room);
            }
        };
    }

    @RequestMapping(
            value = "updateScore",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> updateScore(
            @RequestParam("roomId") int roomId,
            @RequestParam("userId") int userId,
            @RequestParam("curScore") int curScore) {
        ObjectNode room = userService.selectRoomById(roomId);
        if (room.get("roomStatus").asInt() == 2)
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "游戏已结束");
                }
            };
        if (room.get("hostId").asInt() == (userId)) {
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

    @RequestMapping(
            value = "endGame",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> endGame(@RequestParam("roomId") int roomId) {
        userService.endGame(roomId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("msg", "游戏结束");
            }
        };
    }
}
