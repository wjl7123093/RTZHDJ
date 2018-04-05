package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * MyStudyEntity   我要学习页面实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-5 update 结构
 */
public class MyStudyEntity {

    private List<UserInfoBlock> UserInfoBlock;          // 用户信息
    private List<CoursewareBlock> CourseChooseBlock;    // 选修课
    private List<CoursewareBlock> CourseMustBlock;      // 必修课
    private List<CoursewareBlock> CourseLittleBlock;    // 微党课

    public List<MyStudyEntity.UserInfoBlock> getUserInfoBlock() {
        return UserInfoBlock;
    }

    public void setUserInfoBlock(List<MyStudyEntity.UserInfoBlock> userInfoBlock) {
        UserInfoBlock = userInfoBlock;
    }

    public List<CoursewareBlock> getCourseChooseBlock() {
        return CourseChooseBlock;
    }

    public void setCourseChooseBlock(List<CoursewareBlock> courseChooseBlock) {
        CourseChooseBlock = courseChooseBlock;
    }

    public List<CoursewareBlock> getCourseMustBlock() {
        return CourseMustBlock;
    }

    public void setCourseMustBlock(List<CoursewareBlock> courseMustBlock) {
        CourseMustBlock = courseMustBlock;
    }

    public List<CoursewareBlock> getCourseLittleBlock() {
        return CourseLittleBlock;
    }

    public void setCourseLittleBlock(List<CoursewareBlock> courseLittleBlock) {
        CourseLittleBlock = courseLittleBlock;
    }

    class UserInfoBlock {
        private String UserName;
        private String PhotoUrl;
        private int NoStudyNum;
        private int YesStudyNum;
        private String Integral;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }

        public int getNoStudyNum() {
            return NoStudyNum;
        }

        public void setNoStudyNum(int noStudyNum) {
            NoStudyNum = noStudyNum;
        }

        public int getYesStudyNum() {
            return YesStudyNum;
        }

        public void setYesStudyNum(int yesStudyNum) {
            YesStudyNum = yesStudyNum;
        }

        public String getIntegral() {
            return Integral;
        }

        public void setIntegral(String integral) {
            Integral = integral;
        }
    }

    class CoursewareBlock {
        private int NodeId;
        private int ArticleId;
        private String Title;

        public int getNodeId() {
            return NodeId;
        }

        public void setNodeId(int nodeId) {
            NodeId = nodeId;
        }

        public int getArticleId() {
            return ArticleId;
        }

        public void setArticleId(int articleId) {
            ArticleId = articleId;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }
    }

}
