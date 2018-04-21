package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.LoginEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 登录接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-21
 * @update
 */
public interface LoginService {

    /**
     * 登录数据
     * @return
     */
    @FormUrlEncoded
    @POST("postUserLogin")
    Observable<BaseJson<LoginEntity>> postUserLogin(@Field("Account") String account,
                                                    @Field("Password") String password);

}
