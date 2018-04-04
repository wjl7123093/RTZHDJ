package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * MyStudyEntity   我要学习页面实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class MyStudyEntity {

    private List<UserInfoBlock> UserInfoBlock;
    private List<CoursewareBlock> CoursewareBlock;

    public List<MyStudyEntity.UserInfoBlock> getUserInfoBlock() {
        return UserInfoBlock;
    }

    public void setUserInfoBlock(List<MyStudyEntity.UserInfoBlock> userInfoBlock) {
        UserInfoBlock = userInfoBlock;
    }

    public List<MyStudyEntity.CoursewareBlock> getCoursewareBlock() {
        return CoursewareBlock;
    }

    public void setCoursewareBlock(List<MyStudyEntity.CoursewareBlock> coursewareBlock) {
        CoursewareBlock = coursewareBlock;
    }

    class UserInfoBlock {
        private String UserName;
        private String PhotoUrl;
        private String StudyLog;
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

        public String getStudyLog() {
            return StudyLog;
        }

        public void setStudyLog(String studyLog) {
            StudyLog = studyLog;
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
