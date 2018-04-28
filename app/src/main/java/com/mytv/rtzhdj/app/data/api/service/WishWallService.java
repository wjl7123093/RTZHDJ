package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 心愿墙/我的心愿 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-27
 * @update
 */
public interface WishWallService {

    /**
     * 获取我的心愿数据
     * @return
     */
    @FormUrlEncoded
    @POST("postMyWishList")
    Observable<BaseJson<List<MyWishEntity>>> postMyWishList(@Field("UserId") int userId,
                                                            @Field("Type") int type);

}
