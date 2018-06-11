package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 通用 tab 新闻界面接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-7
 * @update
 */
public interface NewsSimpleService {

    /**
     * 获取 带"推荐"通用二级页面
     * @return
     */
    @GET("getTwoLevelList")
    Observable<BaseJson<NewsSimpleEntity>> getTwoLevelList(@Query("CurrentSystemID") int currentSystemId,
                                                           @Query("NodeId") int nodeId,
                                                           @Query("PageIndex") int pageIndex,
                                                           @Query("PageSize") int pageSize);

    /**
     * 获取 二级通用列表
     * @return
     */
    @GET("getTwoLevelInfoList")
    Observable<BaseJson<List<NewsDetailEntity>>> getTwoLevelInfoList(@Query("CurrentSystemID") int currentSystemId,
                                                                     @Query("NodeId") int nodeId,
                                                                     @Query("PageIndex") int pageIndex,
                                                                     @Query("PageSize") int pageSize);

}
