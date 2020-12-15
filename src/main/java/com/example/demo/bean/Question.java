package com.example.demo.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Question {
    int qid;
    String desp;
    String choices;
    String answer;
    String chinese;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> ret = new HashMap<>();

        //convert choices
        String[] pairs = choices.split(",");
        ArrayList<String> c = new ArrayList<>();
        for (int i = 0; i < pairs.length; i++) {
            c.add(pairs[i].split(":")[1]);
        }

        ret.put("qid", qid);
        ret.put("chinese", chinese);
        ret.put("desp", desp);
        ret.put("answer", answer);
        ret.put("choices", c);
        return ret;
    }
}
