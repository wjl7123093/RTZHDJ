package com.mytv.rtzhdj.app.data.entity;

/**
 * Questionaire   问卷实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-8
 * @update
 */
public class QuestionaireEntitiy {

    private int id;
    private String title;           // 标题
    private String start_time;      // 开始时间
    private String end_time;        // 结束时间
    private int status;             // 状态（0 进行中，1 已结束）

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
