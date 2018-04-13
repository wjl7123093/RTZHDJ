package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
     */
    @GET("getUserIdentity")
    Observable<BaseJson<List<UserCategoryEntity>>> getUserCategory();

    /**
     * 获取验证码
     * @return
     */
    @GET("getVerificationCode")
    Observable<VerifyCodeEntity> getVerifyCode(@Query("telNumber") String telNumber);

    /**
     * 注册
     * @return
     */
    @GET("userRegister")
    Observable<UserRegisterEntity> getRegister(@Query("Mobile") String mobile,
                                               @Query("PublishmentSystemId") String publishmentSystemId,
                                               @Query("Password") String password);

}
