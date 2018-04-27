package com.mytv.rtzhdj.app.data.entity;

import java.io.Serializable;

/**
 * UserDetailEntity   党员详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 * @crdate 2018-4-4
 * @update 2018-4-5     update 结构
 *         2018-4-26    update 结构
 */
public class UserDetailEntity implements Serializable {

    private BaseInfo BaseInfo;
    private PartyInfo PartyInfo;

    public UserDetailEntity.BaseInfo getBaseInfo() {
        return BaseInfo;
    }

    public void setBaseInfo(UserDetailEntity.BaseInfo baseInfo) {
        BaseInfo = baseInfo;
    }

    public UserDetailEntity.PartyInfo getPartyInfo() {
        return PartyInfo;
    }

    public void setPartyInfo(UserDetailEntity.PartyInfo partyInfo) {
        PartyInfo = partyInfo;
    }

    public class BaseInfo implements Serializable {
        private int Id;
        private String UserName;                // 姓名
        private String PublishmentsystemName;   // 党支部名称
        private String Sex;                     // 性别
        private String Nation;                  // 民族
        private String Origo;                   // 籍贯
        private String Mobile;                  // 手机
        private String Phone;                   // 电话
        private String EnmergencyMobile;        // 紧急联系电话
        private String OtherContact;            // 其他联系方式
        private String Address;                 // 地址
        private String QQ;                      // QQ
        private String Mailbox;                 // 邮箱

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPublishmentsystemName() {
            return PublishmentsystemName;
        }

        public void setPublishmentsystemName(String publishmentsystemName) {
            PublishmentsystemName = publishmentsystemName;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getNation() {
            return Nation;
        }

        public void setNation(String nation) {
            Nation = nation;
        }

        public String getOrigo() {
            return Origo;
        }

        public void setOrigo(String origo) {
            Origo = origo;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getEnmergencyMobile() {
            return EnmergencyMobile;
        }

        public void setEnmergencyMobile(String enmergencyMobile) {
            EnmergencyMobile = enmergencyMobile;
        }

        public String getOtherContact() {
            return OtherContact;
        }

        public void setOtherContact(String otherContact) {
            OtherContact = otherContact;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getQQ() {
            return QQ;
        }

        public void setQQ(String QQ) {
            this.QQ = QQ;
        }

        public String getMailbox() {
            return Mailbox;
        }

        public void setMailbox(String mailbox) {
            Mailbox = mailbox;
        }
    }

    public class PartyInfo implements Serializable {
        private String UserName;        // 姓名
        private String Birthday;        // 生日日期
        private String IdCode;          // 身份证号
        private String Education;       // 学历
        private String WorkTime;        // 参加工作时间

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String birthday) {
            Birthday = birthday;
        }

        public String getIdCode() {
            return IdCode;
        }

        public void setIdCode(String idCode) {
            IdCode = idCode;
        }

        public String getEducation() {
            return Education;
        }

        public void setEducation(String education) {
            Education = education;
        }

        public String getWorkTime() {
            return WorkTime;
        }

        public void setWorkTime(String workTime) {
            WorkTime = workTime;
        }
    }
}
