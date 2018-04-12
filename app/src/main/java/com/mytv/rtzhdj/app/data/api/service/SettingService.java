package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.UserDetailEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 设置 - 用户详情数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-12
 * @update
 */
public interface SettingService {

    /**
     * 获取用户详情数据
     * @return
     */
    @GET("/getUserDetail")
    Observable<UserDetailEntity> getUserDetail(@Query("userId") int userId);

}
