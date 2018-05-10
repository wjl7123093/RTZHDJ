package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * ScoresDetailsEntity   积分明细实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-10
 * @update
 */
public class ScoresDetailsEntity {

    private MainScore Main;
    private List<DetailScore> Detail;

    public MainScore getMain() {
        return Main;
    }

    public void setMain(MainScore main) {
        Main = main;
    }

    public List<DetailScore> getDetail() {
        return Detail;
    }

    public void setDetail(List<DetailScore> detail) {
        Detail = detail;
    }

    public class MainScore {
        private int AvailableIntegral;
        private int AccumulativeIntegral;

        public int getAvailableIntegral() {
            return AvailableIntegral;
        }

        public void setAvailableIntegral(int availableIntegral) {
            AvailableIntegral = availableIntegral;
        }

        public int getAccumulativeIntegral() {
            return AccumulativeIntegral;
        }

        public void setAccumulativeIntegral(int accumulativeIntegral) {
            AccumulativeIntegral = accumulativeIntegral;
        }
    }

    public class DetailScore {
        private int IntegralValue;
        private int GetType;
        private String GetTime;

        public int getIntegralValue() {
            return IntegralValue;
        }

        public void setIntegralValue(int integralValue) {
            IntegralValue = integralValue;
        }

        public int getGetType() {
            return GetType;
        }

        public void setGetType(int getType) {
            GetType = getType;
        }

        public String getGetTime() {
            return GetTime;
        }

        public void setGetTime(String getTime) {
            GetTime = getTime;
        }
    }

}
