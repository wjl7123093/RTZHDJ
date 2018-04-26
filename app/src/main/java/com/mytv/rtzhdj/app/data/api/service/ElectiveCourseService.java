package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 选修课/微党课数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-26
 * @update
 */
public interface ElectiveCourseService {

    /**
     * 获取 课件列表（选修课/微党课）数据
     * @return
     */
    @GET("getCoursewareList")
    Observable<BaseJson<List<CoursewareEntity>>> getCoursewareList(@Query("UserId") int UserId,
                                                                   @Query("NodeId") int NodeId,
                                                                   @Query("StudyState") int StudyState,
                                                                   @Query("PageIndex") int PageIndex,
                                                                   @Query("PageSize") int PageSize);
}
