package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 志愿服务详情 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface VolunteerServiceDetailService {

    /**
     * 获取志愿服务详情数据
     * @return
     */
    @GET("/getVolunteerServiceDetail")
    Observable<VolunteerDetailEntity> getVolunteerServiceDetail(@Query("id") String id);

}