package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 注册接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-7
 * @update
 */
public interface RegisterService {

    /**
     * 获取用户类型
     * @return
     * @Headers("Cache-Control:public ,max-age=60") 设置接口数据缓存（因为已经统一设置，故现在不需要单独设置）
     */
    @GET("getUserIdentity")
    Observable<BaseJson<List<UserCategoryEntity>>> getUserCategory();

    /**
     * 获取站点集合数据
     * @return
     * @Headers("Cache-Control:public ,max-age=60") 设置接口数据缓存（因为已经统一设置，故现在不需要单独设置）
     */
    @POST("postAllPublishmentSystem")
    Observable<BaseJson<List<StationEntity>>> postAllPublishmentSystem();

    /**
     * 获取验证码
     * @return
     */
    @GET("getVerificationCode")
    Observable<BaseJson<VerifyCodeEntity>> getVerifyCode(@Query("telNumber") String telNumber);

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST("postUserRegister")
    Observable<BaseJson<UserRegisterEntity>> getRegister(@Field("Mobile") String mobile,
                                                         @Field("PublishmentSystemId") int publishmentSystemId,
                                                         @Field("Password") String password);

}
