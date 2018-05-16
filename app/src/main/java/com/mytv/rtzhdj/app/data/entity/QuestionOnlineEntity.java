package com.mytv.rtzhdj.app.data.entity;

/**
 * QuestionOnlineEntity   在线调查列表实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-15
 * @update
 *
 */
public class QuestionOnlineEntity {

    private int ID;
    private String Title;           // 问卷调查标题
    private String StartDate;       // 开始时间
    private String EndDate;         // 结束时间
    private int stat;               // 状态  1 进行中   2 结束

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
