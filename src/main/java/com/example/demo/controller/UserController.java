package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> login(@RequestBody HashMap<String, Object> requestMap) {
        String username = (String) requestMap.get("username");
        String password = (String) requestMap.get("password");
        HashMap<String, Object> res = userService.selectByUsername(username);
        if (res == null) return new HashMap<String, Object>() {
            {
                put("respCode", 2);
                put("msg", "无此用户");
            }
        };
        else if (!res.get("password").equals(password))
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "密码错误");
                }
            };
        else{
            res.remove("password");
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "登录成功");
                    put("data", res);
                }
            };
        }
    }

    @RequestMapping(value = "register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> requestMap) {
        String username = (String) requestMap.get("username");
        if (userService.selectByUsername(username) != null) {
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "该用户名已存在");
                }
            };
        } else {
            int max = 20;
            int min = 1;
            int faceId = (int) (Math.random() * (max - min) + min);
            if (!requestMap.containsKey("faceId")) requestMap.put("faceId", faceId);
            userService.insertIntoUser(requestMap);
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "注册成功");
                }
            };
        }
    }

    @RequestMapping(value = "getRank", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> getRank(@RequestParam("userId") int userId) {
        //get top 10
        List<HashMap<String, Object>> userRankList10 = userService.getRank();
        //get your info
        HashMap<String, Object> yourInfo = userService.selectByUserId(userId);
        yourInfo.remove("password");

        //get your rank
        int yourRankNumber = userService.getRankOfUserById(userId);

        //seal your info
        HashMap<String, Object> yourRankBox = new HashMap<String, Object>() {
            {
                put("data", yourInfo);
                put("rank", yourRankNumber);
            }
        };

        return new HashMap<String, Object>() {
            {
                put("yourRank", yourRankBox);
                put("allRank", userRankList10);
            }
        };

    }

    @RequestMapping(value = "resetPassword", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> resetPassword(@RequestBody HashMap<String, Object> requestMap) {
        String username = (String) requestMap.get("username");
        String password = (String) requestMap.get("password");
        String newPassword = (String) requestMap.get("newPassword");
        HashMap<String, Object> res = userService.selectByUsername(username);
        if (res == null) return new HashMap<String, Object>() {
            {
                put("respCode", 2);
                put("msg", "无此用户");
            }
        };
        else if (!res.get("password").equals(password))
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "密码错误");
                }
            };
        else {
            int userId = (int) res.get("userId");
            userService.updateUserPswById(userId, newPassword);
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "修改成功");
                }
            };
        }
    }

    @RequestMapping(value = "changeFace", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> changeFace(@RequestParam("faceId") int faceId, @RequestParam("userId") int userId) {
        if (faceId > 20) return new HashMap<String, Object>() {
            {
                put("respCode", 2);
                put("msg", "头像ID不合法，超出20");
            }
        };
        else {
            userService.updateFaceId(faceId, userId);
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "更换头像成功");
                }
            };
        }
    }

    @RequestMapping(value = "changeUsername", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> changeUsername(@RequestParam("userId") int userId, @RequestParam("username") String username) {
        if (userService.selectByUsername(username) != null)
            return new HashMap<String, Object>() {
                {
                    put("respCode", 2);
                    put("msg", "用户名已存在");
                }
            };
        else {
            userService.updateUsername(userId, username);
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "更改用户名成功");
                }
            };
        }
    }

    @RequestMapping(value = "getAllMyInfo", produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> allMyInfo(@RequestParam("userId") int userId) {
        HashMap<String, Object> user = userService.selectByUserId(userId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("userInfo", user);
            }
        };
    }

    @RequestMapping(path = "/getAnonymousId")
    public HashMap<String, Object> getAnonymousId() {
        HashMap<String, Object> requestMap = new HashMap<String, Object>() {
            {
                put("username", "匿名用户");
                put("password", "Guest");
                put("anonymous", 1);
            }
        };
        int max = 20;
        int min = 1;
        int faceId = (int) (Math.random() * (max - min) + min);
        if (!requestMap.containsKey("faceId")) requestMap.put("faceId", faceId);
        int userId = userService.insertIntoUser(requestMap);
        HashMap<String, Object> user = userService.selectByUserId(userId);
        return new HashMap<String, Object>() {
            {
                put("userId", userId);
                put("userInfo", user);
                put("respCode", 1);
                put("msg", "成功");
            }
        };
    }
}
