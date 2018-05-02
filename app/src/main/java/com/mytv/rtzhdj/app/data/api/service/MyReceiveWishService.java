package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 我认领的心愿 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-2
 * @update
 */
public interface MyReceiveWishService {

    /**
     * 获取我认领的心愿数据
     * @return
     */
    @FormUrlEncoded
    @POST("postMyClaimWishList")
    Observable<BaseJson<List<MyWishEntity>>> postMyClaimWishList(@Field("UserId") int userId,
                                                                 @Field("Type") int type);

}
