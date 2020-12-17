package com.example.demo.controller.battle;

import com.example.demo.config.ResponseMessage;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, origins = "*")
public class BattleController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path = "/battle/peek")
    public Battle peek(int id) {
        HashMap<String, Object> room = userService.selectRoomById(id);
        return Battle.fromRoom(room);
    }

    @RequestMapping(path = "/battle/new")
    public Battle createNewBattle(@RequestParam int id) {
        int roomId = userService.insertIntoRoom(id);
        questionService.insertQuestionsInRoom(roomId);

        Battle battle = new Battle(id);
        battle.setId(roomId);

        return battle;
    }

    private boolean isCreator(@RequestParam int userId, @RequestParam int battleId) {
        HashMap<String, Object> room = userService.selectRoomById(battleId);
        Number hostId = (Number) room.get("hostId");
        return hostId.intValue() == userId;
    }

    private boolean addUpScoreOf(int userId, int addition) {
        HashMap<String, Object> user = userService.selectByUserId(userId);
        if (user == null) {
            return false;
        }
        int score = ((Number) user.getOrDefault("score", 0)).intValue();
        score += addition;
        userService.updateScoreById(score, userId);
        return true;
    }

    @RequestMapping(path = "/battle/saveScore")
    public HashMap<String, Object> saveScore(@RequestParam int id, @RequestParam int battleId) {
        HashMap<String, Object> room = userService.selectRoomById(battleId);
        if (room==null){
            throw new NoSuchElementException("Room not found:"+battleId);
        }
        if (((Number) room.get("roomStatus")).intValue() == -3) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("errorMsg", ResponseMessage.NOT_AVAILABLE + battleId);
                }
            };
        }
        //room status = -3
        userService.finishBattle(battleId);

        Battle battle = Battle.fromRoom(room);

        int creatorId = battle.getCreatorId();
        int guestId = battle.getGuestId();
        long creatorCredits = battle.getCreatorCredits();
        long guestCredits = battle.getGuestCredits();

        if (guestId == creatorId) { //自娱自乐的用户，取host/guest中最高分
            addUpScoreOf(guestId, (int) (Math.max(guestCredits, creatorCredits)));
        } else {
            addUpScoreOf(creatorId, (int) creatorCredits);
            addUpScoreOf(guestId, (int) guestCredits);
        }

        HashMap<String, Object> retUser = userService.selectByUserId(id);
        retUser.remove("password");
        return new HashMap<String, Object>() {
            {
                put("userInfo", retUser);
                put("respCode", 1);
            }
        };
    }

    @RequestMapping(path = "/battle/step_in")
    public Battle stepIn(@RequestParam int id, @RequestParam int battleId) {
        userService.enterRoom(id, battleId);
        HashMap<String, Object> room = userService.selectRoomById(battleId);
        return Battle.fromRoom(room);
    }

    @RequestMapping(path = "/battle/refresh")
    public Battle getBattleInfo(@RequestParam int id) {
        HashMap<String, Object> room = userService.selectRoomById(id);
        return Battle.fromRoom(room);
    }

    @RequestMapping(path = "/battle/update/credit")
    public HashMap<String, Object> updateCredit(@RequestParam int user, @RequestParam int battle, @RequestParam int total) {
        if (isCreator(user, battle)) {
            userService.updateHostScore(total, battle);
        } else {
            userService.updateGuestScore(total, battle);
        }
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("msg", ResponseMessage.SUCCESS);
            }
        };
    }


}
