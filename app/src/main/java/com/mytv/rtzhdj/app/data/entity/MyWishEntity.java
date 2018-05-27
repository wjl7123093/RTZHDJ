package com.mytv.rtzhdj.app.data.entity;

/**
 * MyWishEntity   我的心愿实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-27
 * @update
 */
public class MyWishEntity {

    private int ID;
    private String Content;
    private boolean AuditStatus;
    private String AllImgUrl;

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

    public boolean isAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        AuditStatus = auditStatus;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
