package com.mytv.rtzhdj.app.data.entity;

/**
 * MembershipEntity   组织关系实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-24
 * @update
 */
public class MembershipEntity {

    private String TransFromOrganization;
    private String TransInOrganization;
    private String OperateDate;

    public String getTransFromOrganization() {
        return TransFromOrganization;
    }

    public void setTransFromOrganization(String transFromOrganization) {
        TransFromOrganization = transFromOrganization;
    }

    public String getTransInOrganization() {
        return TransInOrganization;
    }

    public void setTransInOrganization(String transInOrganization) {
        TransInOrganization = transInOrganization;
    }

    public String getOperateDate() {
        return OperateDate;
    }

    public void setOperateDate(String operateDate) {
        OperateDate = operateDate;
    }
}
