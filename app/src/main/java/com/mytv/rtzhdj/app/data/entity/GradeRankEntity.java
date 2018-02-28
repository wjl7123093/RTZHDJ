package com.mytv.rtzhdj.app.data.entity;

/**
 * GradeRankEntity   成绩排名实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update
 */
public class GradeRankEntity {

    private int id;
    private String name;
    private String party_branch;
    private String img_url;
    private String time;
    private int grade;
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty_branch() {
        return party_branch;
    }

    public void setParty_branch(String party_branch) {
        this.party_branch = party_branch;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
