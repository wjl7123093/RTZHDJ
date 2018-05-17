package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * GradeRankEntity   成绩排名实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update 2018-5-16    update 结构
 */
public class GradeRankEntity {

    private int MyScore;
    private int TotalScore;
    private String Ranking;
    private String Achievement;
    private String Times;
    private List<GradeRank> TestCompareList;

    public int getMyScore() {
        return MyScore;
    }

    public void setMyScore(int myScore) {
        MyScore = myScore;
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int totalScore) {
        TotalScore = totalScore;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public String getAchievement() {
        return Achievement;
    }

    public void setAchievement(String achievement) {
        Achievement = achievement;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public List<GradeRank> getTestCompareList() {
        return TestCompareList;
    }

    public void setTestCompareList(List<GradeRank> testCompareList) {
        TestCompareList = testCompareList;
    }

    public class GradeRank {

        public GradeRank() {}

        private int UserID;
        private String UserName;
        private String PublishmentSystemName;
        private int Score;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int userID) {
            UserID = userID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPublishmentSystemName() {
            return PublishmentSystemName;
        }

        public void setPublishmentSystemName(String publishmentSystemName) {
            PublishmentSystemName = publishmentSystemName;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int score) {
            Score = score;
        }
    }
}
