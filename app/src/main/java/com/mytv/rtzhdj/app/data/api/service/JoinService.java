package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 我要参与数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface JoinService {

    /**
     * 获取我要参与数据
     * @return
     */
    @GET("/getMyPartIn")
    Observable<MyJoinEntity> getMyPartIn(@Query("userId") int userId,
                                         @Query("count") int count);

}
