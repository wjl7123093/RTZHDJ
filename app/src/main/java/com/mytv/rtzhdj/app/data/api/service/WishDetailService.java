package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    /**
     * post 编辑许愿
     * @return
     */
    @Multipart
    @POST("postMyWish")
    Observable<BaseJson> postEditMyWish(@PartMap Map<String, RequestBody> params,
                                        @Part List<MultipartBody.Part> parts);

    /**
     * 删除 心愿
     * @return
     */
    @FormUrlEncoded
    @POST("postWishDetail")
    Observable<BaseJson> postDeleteMyWish(@Field("WishID") int wishId);

}
