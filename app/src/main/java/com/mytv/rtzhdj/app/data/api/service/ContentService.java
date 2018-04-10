package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 新闻列表 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-9
 * @update
 */
public interface ContentService {

    /**
     * 获取党建新闻推荐列表数据
     * @return
     */
    @GET("/getPartyRecommend")
    Observable<PartyRecommendEntity> getPartyRecommend(@Query("typeId") String typeId,
                                                       @Query("count") int count);

    /**
     * 获取党建新闻二级列表数据
     * @return
     */
    @GET("/getPartySubList")
    Observable<PartySubNewsEntity> getPartySubList(@Query("NodeId") int nodeId,
                                                   @Query("typeId") String typeId,
                                                   @Query("count") int count);

}