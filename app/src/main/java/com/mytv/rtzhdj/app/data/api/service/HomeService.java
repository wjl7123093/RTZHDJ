package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 首页数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-8
 * @update
 */
public interface HomeService {

    /**
     * 获取首页数据
     * @return
     */
    @GET("getHomeInfo")
    Observable<HomeEntity> getHomeData(@Query("CurUserId") int curUserId,
                                       @Query("PageSize") int pageSize);

}
