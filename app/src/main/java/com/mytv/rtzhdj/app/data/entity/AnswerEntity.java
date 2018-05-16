package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * AnswerEntity   问卷答案实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-15
 * @update
 */
public class AnswerEntity {

//    private int Id;                 // 答案id
    private String Content;         // 答案
    private int ans_state;          // 答案是否被解答

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getAns_state() {
        return ans_state;
    }

    public void setAns_state(int ans_state) {
        this.ans_state = ans_state;
    }
}
