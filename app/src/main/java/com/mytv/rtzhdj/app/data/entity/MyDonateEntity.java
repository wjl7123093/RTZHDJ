package com.mytv.rtzhdj.app.data.entity;

/**
 * MyDonateEntity   我的捐赠实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-3
 * @update
 */
public class MyDonateEntity {

    private int ID;
    private String Content;
    private String Phone;
    private String Topic;
    private String Imgurl;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }
}
