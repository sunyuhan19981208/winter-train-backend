package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
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

    /**
     * 前端传来的是json，那么只能使用map或者string接收，不能直接自动解析，所以需要前端一同改造。
     * 直接自动解析参数需要使用GET的参数或者post的http-form-data的形式。
     * @param params
     * @return
     */
    @RequestMapping(
            value = "login",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> login(@RequestBody HashMap<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        HashMap<String, Object> res = userService.selectByUsername(username);
        if (res == null)
            return new HashMap<String, Object>() {
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

    @RequestMapping(
            value = "register",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> register(@RequestBody ObjectNode params) {
        String username = params.get("username").asText();
        String password = params.get("password").asText();
        int faceId = params.get("faceId").asInt(0);
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

            userService.insertIntoUser(username, password, faceId, 0);
            return new HashMap<String, Object>() {
                {
                    put("respCode", 1);
                    put("msg", "注册成功");
                }
            };
        }
    }

    /**
     * TODO:前端的排行页面会卡死
     * @param userId
     * @return
     */
    @GetMapping(
            value = "getRank",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> getRank(@RequestParam("userId") int userId) {
        // get top 10
        List<HashMap<String, Object>> userRankList10 = userService.getRank();
        // get your info
        HashMap<String, Object> yourInfo = userService.selectByUserId(userId);
        yourInfo.remove("password");

        // get your rank
        int yourRankNumber = userService.getRankOfUserById(userId);

        // seal your info
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

    /**
     * TODO:请求参数如果使用json格式，那么不能直接解析。
     * 考虑尝试使用自定义解析器实现自动解析json参数 https://jiacyer.com/2019/01/23/Java-Spring-form-json-compatibility/
     * @param params
     * @return
     */
    @RequestMapping(
            value = "resetPassword",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> resetPassword(@RequestBody HashMap<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String newPassword = params.get("newPassword");
        HashMap<String, Object> res = userService.selectByUsername(username);
        if (res == null)
            return new HashMap<String, Object>() {
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

    @RequestMapping(
            value = "changeFace",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> changeFace(@RequestParam("faceId") int faceId, @RequestParam("userId") int userId) {
        if (faceId > 20)
            return new HashMap<String, Object>() {
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

    @RequestMapping(
            value = "changeUsername",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> changeUsername(
            @RequestParam("userId") int userId, @RequestParam("username") String username) {
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

    @GetMapping(
            value = "getAllMyInfo",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<String, Object> allMyInfo(@RequestParam("userId") int userId) {
        HashMap<String, Object> user = userService.selectByUserId(userId);
        return new HashMap<String, Object>() {
            {
                put("respCode", 1);
                put("userInfo", user);
            }
        };
    }

    /**
     * TODO:使用线程安全、全局唯一的匿名id
     * @return
     */
    @GetMapping(path = "/getAnonymousId")
    public HashMap<String, Object> getAnonymousId() {
        int max = 20;
        int min = 1;
        int faceId = (int) (Math.random() * (max - min) + min);
        int userId = userService.insertIntoUser("匿名用户", "Guest", faceId, 1);
        HashMap<String, Object> user = userService.selectByUserId(userId);
        user.put("username", "匿名用户");
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
