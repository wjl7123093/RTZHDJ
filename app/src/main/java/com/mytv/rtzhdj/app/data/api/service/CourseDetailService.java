package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 课件详情数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update 2017-5-23
 */
public interface CourseDetailService {

    /**
     * 获取课件详情数据
     * @return
     */
    @GET("getCoursewareDetail")
    Observable<BaseJson<CoursewareDetailEntity>> getCoursewareDetail(@Query("id") int id);

    /**
     * 获取课件详情数据
     * @return
     */
    @FormUrlEncoded
    @POST("PostStudyClass")
    Observable<BaseJson> postStudyClass(@Field("UserID") int userId,
                                        @Field("NodeID") int nodeId,
                                        @Field("ContentID") int contentId);

}
