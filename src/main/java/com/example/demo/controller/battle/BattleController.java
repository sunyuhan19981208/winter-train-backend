package com.example.demo.controller.battle;

import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public Battle createNewBattle(int id) {
        int roomId = userService.insertIntoRoom(id);
        questionService.insertQuestionsInRoom(roomId);

        Battle battle = new Battle(id);
        battle.setId(roomId);

//        BattleRepo.put(battle.getId(), battle);
        return battle;
    }

    private boolean isCreator(int userId, int battleId) {
        HashMap<String, Object> room = userService.selectRoomById(battleId);
        Number hostId = (Number) room.get("hostId");
        return hostId.intValue() == userId;
    }

    @RequestMapping(path = "/battle/saveScore")
    public HashMap<String, Object> saveScore(int id, int battleId) {
        HashMap<String, Object> room = userService.selectRoomById(battleId);
        Battle battle = Battle.fromRoom(room);

        HashMap<String, Object> user = userService.selectByUserId(id);
        if (user == null) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("errorMsg", "No such user id: " + id);
                }
            };
        }
        Number scoreNumber = (Number) user.getOrDefault("score", 0);
        int score = scoreNumber.intValue();
        if (isCreator(id, battleId)) {
            score = (int) battle.getCreatorCredits();
            userService.updateHostScore(battleId, score);
        } else {
            score = (int) battle.getGuestCredits();
            userService.updateGuestScore(battleId, score);
        }
        return userService.selectByUserId(id);
    }

    @RequestMapping(path = "/battle/step_in")
    public Battle stepIn(int id, int battleId) {
        userService.enterRoom(id, battleId);
        HashMap<String, Object> room = userService.selectRoomById(battleId);
        return Battle.fromRoom(room);
    }

    @RequestMapping(path = "/battle/refresh")
    public Battle getBattleInfo(int id) {
        HashMap<String, Object> room = userService.selectRoomById(id);
        return Battle.fromRoom(room);
    }

    @RequestMapping(path = "/battle/questions")
    public HashMap<String, String> getQuestions(String id) {

        return new HashMap<String, String>() {
            {
                put("apple", "苹果");
                put("orange", "橙子");
                put("banana", "香蕉");
                put("peach", "桃子");
            }
        };
    }

    @RequestMapping(path = "/battle/update/credit")
    public void updateCredit(int user, int battle, int total) {
        if (isCreator(user, battle)) {
            userService.updateHostScore(total, battle);
        } else {
            userService.updateGuestScore(total, battle);
        }
    }

    @RequestMapping(path = "/battle/update/finish")
    public void reportFinish(int battleId, int id) {
//        BattleRepo.remove(battleId);
    }
}
