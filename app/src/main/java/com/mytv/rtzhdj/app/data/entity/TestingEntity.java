package com.mytv.rtzhdj.app.data.entity;

/**
 * TestingEntity   效果测评实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update
 */
public class TestingEntity {

    private int id;
    private String title;
    private String start_time;
    private String end_time;
    private String last_time;
    private int scores;
    private int total_num;
    private int last_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getLast_num() {
        return last_num;
    }

    public void setLast_num(int last_num) {
        this.last_num = last_num;
    }
}
