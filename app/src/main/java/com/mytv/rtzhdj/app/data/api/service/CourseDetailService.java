package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 课件详情数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update
 */
public interface CourseDetailService {

    /**
     * 获取课件详情数据
     * @return
     */
    @GET("/getCoursewareDetail")
    Observable<CoursewareDetailEntity> getCoursewareDetail(@Query("id") int id);

}
