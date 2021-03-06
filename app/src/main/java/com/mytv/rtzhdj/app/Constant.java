package com.mytv.rtzhdj.app;

/**
 * Constant 存放基本常量
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-22
 * @update 2018-1-23    新增 typeImage, typeHeader 类型
 *         2018-2-2     新增 typeColumn, typeSingle 类型
 */
public class Constant {

    // RecyclerView item 类型
    // 如果 item 布局不一致，那么 type 就必须不一致，否则就会导致 滑动卡顿
    // e.g. 两个不同布局的 item，却使用同一个 type，那么就会导致 滑动卡顿
    public interface viewType{
        int typeTopHeader = 0;      //home 顶部头部
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeList = 4;           //list
        int typeNews = 5;           //新闻
        int typeMarquee = 6;        //跑马灯
        int typePlus = 7 ;          //不规则视图
        int typeColumn = 8;         //格栏布局
        int typeFooter = 9;         //底部
        int typeGvSecond = 10;      //九宫格
        int typeImage = 11;         //图片
        int typeHeader = 12;        //头部
        int typeSingle = 13;        //通栏布局

        int typeScores = 14;        //积分栏
        int typeCash = 15;          //缴费栏
    }

    // RecyclerView item 类型
    // Settings 页面
    public interface viewTypeSettings{
        int typeHeader1 = 1;        //头像1
        int typeHeader2 = 2;        //头像2
        int typeInfo1 = 3;          //信息栏1
        int typeInfo2 = 4;          //信息栏2
        int typeInfo3 = 5;          //信息栏3
        int typeInfo4 = 7;          //信息栏3
        int typeMobile = 6;         //手机
    }

}
