package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.MyStudyEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 我要学习数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-9
 * @update
 */
public interface StudyService {

    /**
     * 获取我要学习数据
     * @return
     */
    @GET("getMyStudy")
    Observable<MyStudyEntity> getMyStudyData(@Query("UserId") int userId,
                                             @Query("count") int count);

}
