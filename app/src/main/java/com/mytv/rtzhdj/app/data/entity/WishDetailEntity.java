package com.mytv.rtzhdj.app.data.entity;

/**
 * WishDetailEntity   心愿详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-28
 * @update
 */
public class WishDetailEntity {

    private String wishPublisher;
    private String content;
    private String phone;
    private String wishTime;

    public String getWishPublisher() {
        return wishPublisher;
    }

    public void setWishPublisher(String wishPublisher) {
        this.wishPublisher = wishPublisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWishTime() {
        return wishTime;
    }

    public void setWishTime(String wishTime) {
        this.wishTime = wishTime;
    }
}
