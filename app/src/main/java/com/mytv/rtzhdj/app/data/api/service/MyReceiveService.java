package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 我领取的物品 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-3
 * @update
 */
public interface MyReceiveService {

    /**
     * 获取我领取的物品数据
     * @return
     */
    @FormUrlEncoded
    @POST("postMyClaimGoodsList")
    Observable<BaseJson<List<MyDonateEntity>>> postMyClaimGoodsList(@Field("UserId") int userId,
                                                                     @Field("Type") int type);

}
