package com.mytv.rtzhdj.app.data.entity;

/**
 * UserDetailEntity   党员详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 * @crdate 2018-4-4
 * @update 2018-4-5 update 结构
 */
public class UserDetailEntity {

    private BaseInfo baseInfo;
    private PartyInfo partyInfo;

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public PartyInfo getPartyInfo() {
        return partyInfo;
    }

    public void setPartyInfo(PartyInfo partyInfo) {
        this.partyInfo = partyInfo;
    }

    class BaseInfo {
        private int id;
        private String userName;                // 姓名
        private String PublishmentsystemName;   // 党支部名称
        private String sex;                     // 性别
        private String nation;                  // 民族
        private String mobile;                  // 手机
        private String phone;                   // 电话
        private String enmergencyMobile;        // 紧急联系电话
        private String otherContact;            // 其他联系方式
        private String address;                 // 地址
        private String qq;                      // QQ
        private String mailbox;                 // 邮箱

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPublishmentsystemName() {
            return PublishmentsystemName;
        }

        public void setPublishmentsystemName(String publishmentsystemName) {
            PublishmentsystemName = publishmentsystemName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEnmergencyMobile() {
            return enmergencyMobile;
        }

        public void setEnmergencyMobile(String enmergencyMobile) {
            this.enmergencyMobile = enmergencyMobile;
        }

        public String getOtherContact() {
            return otherContact;
        }

        public void setOtherContact(String otherContact) {
            this.otherContact = otherContact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getMailbox() {
            return mailbox;
        }

        public void setMailbox(String mailbox) {
            this.mailbox = mailbox;
        }
    }

    class PartyInfo {
        private String userName;        // 姓名
        private String dateOfBirth;     // 生日日期
        private String cartID;          // 身份证号
        private int individualStatus;   //
        private String wordTime;        //
    }
}
