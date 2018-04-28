package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 心愿详情 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-28
 * @update
 */
public interface WishDetailService {

    /**
     * 获取 心愿详情
     * @return
     */
    @FormUrlEncoded
    @POST("postWishDetail")
    Observable<BaseJson<WishDetailEntity>> postWishDetail(@Field("WishID") int wishId);

}
