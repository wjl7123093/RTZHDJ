package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.SignEntity;
import com.mytv.rtzhdj.app.data.entity.SignScoresEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 签到接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-2
 * @update
 */
public interface SigninService {

    /**
     * 签到
     * @return
     */
    @FormUrlEncoded
    @POST("postSignForScore")
    Observable<BaseJson<SignScoresEntity>> postSignForScore(@Field("userID") int userId);

    /**
     * 获取签到信息
     * @return
     */
    @GET("GetSignList")
    Observable<BaseJson<SignEntity>> getSignList(@Query("UserID") int userId);

}
