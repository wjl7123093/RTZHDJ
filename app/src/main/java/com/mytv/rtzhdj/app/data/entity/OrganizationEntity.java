package com.mytv.rtzhdj.app.data.entity;

/**
 * OrganizationEntity   组织活动实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-22
 * @update
 */
public class OrganizationEntity {

    private int ID;
    private String Title;
    private int Digs;
    private int Comments;
    private String Address;

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

    public int getDigs() {
        return Digs;
    }

    public void setDigs(int digs) {
        Digs = digs;
    }

    public int getComments() {
        return Comments;
    }

    public void setComments(int comments) {
        Comments = comments;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
