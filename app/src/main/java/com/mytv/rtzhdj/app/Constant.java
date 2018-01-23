package com.mytv.rtzhdj.app;

/**
 * Constant 存放基本常量
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-22
 * @update 2018-1-23    新增 typeImage, typeHeader 类型
 */
public class Constant {

    // RecyclerView item 类型
    public interface viewType{
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeList = 4;           //list
        int typeNews = 5;           //新闻
        int typeMarquee = 6;        //跑马灯
        int typePlus = 7 ;          //不规则视图
        int typeSticky = 8;         //指示器
        int typeFooter = 9;         //底部
        int typeGvSecond = 10;      //九宫格
        int typeImage = 11;         //图片
        int typeHeader = 12;        //头部
        int typeOnePlusN = 13;      //一拖N
    }

}