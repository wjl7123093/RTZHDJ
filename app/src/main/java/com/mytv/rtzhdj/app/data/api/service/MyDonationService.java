package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 我的捐赠 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-3
 * @update
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

}
