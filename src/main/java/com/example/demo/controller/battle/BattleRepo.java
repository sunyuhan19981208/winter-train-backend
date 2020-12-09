package com.example.demo.controller.battle;

import java.util.HashMap;
import java.util.Map;

public class BattleRepo {
    private static Map<Number, Battle> battles = new HashMap<>();

    public static Battle get(Number key) {
        return battles.get(key);
    }

    public static Battle put(Number key, Battle value) {
        return battles.put(key, value);
    }

    public static Battle remove(Number o) {
        return battles.remove(o);
    }
}
