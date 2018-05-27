package com.mytv.rtzhdj.app.data.entity;

/**
 * ReportCommunityEntity   报到社区 实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-27
 * @update
 */
public class ReportCommunityEntity {

    private int PublishmentSystemId;
    private String PublishmentSystemName;
    private String Mobile;

    public int getPublishmentSystemId() {
        return PublishmentSystemId;
    }

    public void setPublishmentSystemId(int publishmentSystemId) {
        PublishmentSystemId = publishmentSystemId;
    }

    public String getPublishmentSystemName() {
        return PublishmentSystemName;
    }

    public void setPublishmentSystemName(String publishmentSystemName) {
        PublishmentSystemName = publishmentSystemName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
