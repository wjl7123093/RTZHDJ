package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 我的/所有捐赠 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-3
 * @update 2017-5-5     新增 所有捐赠接口
 */
public interface MyDonationService {

    /**
     * 获取我的捐赠数据
     * @return
     */
    @FormUrlEncoded
    @POST("postMyClaimDonateList")
    Observable<BaseJson<List<MyDonateEntity>>> postMyClaimDonateList(@Field("UserId") int userId,
                                                                     @Field("Type") int type);

    /**
     * 获取所有捐赠数据
     * @return
     */
    @GET("getAllDonateList")
    Observable<BaseJson<List<MyDonateEntity>>> getAllDonateList(@Query("UserId") int userId,
                                                                @Query("TypeId") int typeId,
                                                                @Query("PageIndex") int pageIndex,
                                                                @Query("PageSize") int pageSize);

}
