package com.mytv.rtzhdj.app.data.entity;

/**
 * TeacherEntity   实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-28
 * @update
 */
public class TeacherEntity {

    private int ID;
    private String Name;
    private String TelePhone;
    private String AllImgUrl;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelePhone() {
        return TelePhone;
    }

    public void setTelePhone(String telePhone) {
        TelePhone = telePhone;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
