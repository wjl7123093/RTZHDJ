package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
     */
    @FormUrlEncoded
    @GET("/getUserIdentity")
    Observable<List<UserCategoryEntity>> getUserCategory();

    /**
     * 获取验证码
     * @return
     */
    @FormUrlEncoded
    @GET("/getVerificationCode")
    Observable<VerifyCodeEntity> getVerifyCode(@Field("telNumber") String telNumber);

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @GET("/userRegister")
    Observable<UserRegisterEntity> getRegister(@Field("Mobile") String mobile,
                                               @Field("PublishmentSystemId") String publishmentSystemId,
                                               @Field("Password") String password);

}
