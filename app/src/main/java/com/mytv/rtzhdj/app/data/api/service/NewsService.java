package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 党建新闻 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-9
 * @update
 */
public interface NewsService {

    /**
     * 获取党建新闻二级栏目数据
     * @return
     */
    @GET("getPartyColumns")
    Observable<PartyColumnsEntity> getPartyColumns(@Query("typeId") String typeId);

}
