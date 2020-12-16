package com.example.demo.controller.battle;

import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
        if (room != null && ((Number) room.get("roomStatus")).intValue() == -3) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("errorMsg", "No such room or is it finished:" + battleId);
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
        userService.updateScoreById(creatorId, (int) creatorCredits);
        userService.updateScoreById(guestId, (int) guestCredits);

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

//    @RequestMapping(path = "/battle/questions")
//    public HashMap<String, String> getQuestions(@RequestParam String id) {
//
//        return new HashMap<String, String>() {
//            {
//                put("apple", "苹果");
//                put("orange", "橙子");
//                put("banana", "香蕉");
//                put("peach", "桃子");
//            }
//        };
//    }

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
                put("msg", "success");
            }
        };
    }


}
