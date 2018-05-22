package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * QuestionEntity   问卷问题实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-15
 * @update
 */
public class QuestionEntity {

    private int Id;                 // 问题id
    private String Title;           // 问题
    private int IsMulti;            // 是否多选
    private String CorrectAnswer;   // 正确答案
    private int Score;              // 分数
    private List<AnswerEntity> Items;   // 答案列表
    private int que_state;              // 是否解答

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getIsMulti() {
        return IsMulti;
    }

    public void setIsMulti(int isMulti) {
        IsMulti = isMulti;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public List<AnswerEntity> getItems() {
        return Items;
    }

    public void setItems(List<AnswerEntity> items) {
        this.Items = items;
    }

    public int getQue_state() {
        return que_state;
    }

    public void setQue_state(int que_state) {
        this.que_state = que_state;
    }
}
