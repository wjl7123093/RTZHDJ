package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 新闻详情 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface NewsDetailService {

    /**
     * 获取 新闻详情
     * @return
     */
    @GET("getContent")
    Observable<BaseJson<NewsDetailEntity>> getContent(@Query("Id") int id,
                                                      @Query("NodeId") int nodeId);

}
