package com.mytv.rtzhdj.app.data.api;

/**
 * 定义 Api 统一接口 URL
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-12-31
 * @update 2018-4-21    update
 */
public interface Api {

    // 服务端地址
    String APP_DOMAIN = "http://61.157.136.102:8903/api/";
    // 图片地址
    String APP_IMAGE_DOMAIN = "http://192.168.13.93";
    // 文章详情地址
    String APP_ARTICLE_DOMAIN = "http://61.157.136.102:8904/mobile/details.html?";
    // 学习课件地址
    String APP_COURSE_DOMAIN = "http://61.157.136.102:8904/mobile/studydetails.html?";
    int RequestSuccess = 200;

}
