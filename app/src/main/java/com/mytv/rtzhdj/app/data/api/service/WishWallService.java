package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;

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

    /**
     * 获取心愿墙数据
     * @return
     */
    @GET("getWishList")
    Observable<BaseJson<List<MyWishEntity>>> getWishList(@Query("UserId") int userId,
                                                         @Query("Type") int type,
                                                         @Query("PageIndex") int pageIndex,
                                                         @Query("PageSize") int pageSize);
    /**
     * post 我要许愿
     * @return
     */
    @Multipart
    @POST("postMyWish")
    Observable<BaseJson> postMyWish(@PartMap Map<String, RequestBody> params,
                                    @Part List<MultipartBody.Part> parts);

}
