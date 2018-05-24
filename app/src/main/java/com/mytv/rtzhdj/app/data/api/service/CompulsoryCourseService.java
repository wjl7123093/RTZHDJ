package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 必修课数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-25
 * @update
 */
public interface CompulsoryCourseService {

    /**
     * 获取 必修课数据
     * @return
     */
    @GET("getCoursewareList")
    Observable<BaseJson<List<CoursewareEntity>>> getCoursewareList(@Query("UserId") int UserId,
                                                                   @Query("NodeId") int NodeId,
                                                                   @Query("StudyState") int StudyState,
                                                                   @Query("PageIndex") int PageIndex,
                                                                   @Query("PageSize") int PageSize);

    /**
     * 获取 头部积分信息
     * @return
     */
    @GET("GetMyScore")
    Observable<BaseJson<HeaderIntegralEntity>> getMyScore(@Query("UserId") int UserId);

}
