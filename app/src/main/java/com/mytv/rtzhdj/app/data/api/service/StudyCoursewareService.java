package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.StudyCoursewareEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 学习课件数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update
 */
public interface StudyCoursewareService {

    /**
     * 获取学习课件数据
     * @return
     */
    @GET("/getCoursewareList")
    Observable<StudyCoursewareEntity> getCoursewareList(@Query("typeId") String typeId);

}
