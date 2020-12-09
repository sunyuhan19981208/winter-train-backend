package com.example.demo.controller.battle;

import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
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

//    @RequestMapping(path = "/battle/peek")
//    public Map<String, Battle> peek() {
//        return BattleRepo.getBattles();
//    }
//
//    @RequestMapping(path = "/battle/clear")
//    public Map<String, Battle> clearBattles() {
//        BattleRepo.clear();
//        return BattleRepo.getBattles();
//    }

    @RequestMapping(path = "/battle/new")
    public Battle createNewBattle(HttpSession session, int id, @RequestParam(defaultValue = "no") String test) {
        HashMap<String, Object> byUsername = userService.selectRoomById(id);


        Battle battle = new Battle(id);
        if ("yes".equals(test)) {
            battle.setId(123);
        }

        BattleRepo.put(battle.getId(), battle);

        return battle;
    }

    private boolean isCreator(int userId, int battleId) {
        Battle battle = BattleRepo.get(battleId);
        return battle.getCreatorId() == userId;
    }

    @RequestMapping(path = "/battle/saveScore")
    public HashMap<String, Object> saveScore(int id, int battleId) {
        Battle battle = BattleRepo.get(battleId);
        HashMap<String, Object> user = userService.selectByUserId(id);
        Number scoreNumber = (Number) user.get("score");
        int score = scoreNumber.intValue();
        if (isCreator(id, battleId)) {
            score += battle.getCreatorCredits();
            userService.updateHostScore(battleId, score);
        } else {
            score += battle.getGuestCredits();
            userService.updateGuestScore(battleId, score);
        }
        return  userService.selectByUserId(id);
    }

    @RequestMapping(path = "/battle/step_in")
    public Battle stepIn(int id, int battleId) {
        Battle battle = BattleRepo.get(battleId);
        battle.setGuestId(id);
        return battle;
    }

    @RequestMapping(path = "/battle/refresh")
    public Battle getBattleInfo(int id) {
        return BattleRepo.get(id);
    }

    @RequestMapping(path = "/battle/questions")
    public HashMap<String, String> getQuestions(String id) {
        HashMap<String, String> ret = new HashMap<>();
        ret.put("apple", "苹果");
        ret.put("orange", "橙子");
        ret.put("banana", "香蕉");
        ret.put("peach", "桃子");
        return ret;
    }

    @RequestMapping(path = "/battle/update/credit")
    public void updateCredit(int user, int battle, int total) {
        Battle battleObject = BattleRepo.get(battle);
        if (user == battleObject.getCreatorId()) {
            battleObject.setCreatorCredits(total);
            battleObject.increaseCreatorQuestionIndex();
        } else {
            battleObject.setGuestCredits(total);
            battleObject.increaseGuestQuestionIndex();
        }
    }

    @RequestMapping(path = "/battle/update/finish")
    public void reportFinish(int battleId, int id) {
        BattleRepo.remove(battleId);
    }

}
