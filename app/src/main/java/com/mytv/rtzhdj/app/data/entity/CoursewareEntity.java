package com.mytv.rtzhdj.app.data.entity;

/**
 * CoursewareEntity   学习课件实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-6
 * @update
 */
public class CoursewareEntity {

    private int id;
    private String title;       // 标题
    private String datetime;    // 上次学习时间
    private int type;           // 课件类型（1 必修，2 选修，3 微党课）

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
