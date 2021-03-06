package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("getVolunteerServiceDetail")
    Observable<BaseJson<VolunteerDetailEntity>> getVolunteerServiceDetail(@Query("Id") int id,
                                                                          @Query("UserId") int userId);

    /**
     * 点赞
     * @return
     */
    @FormUrlEncoded
    @POST("PostDoDigForVolunteer")
    Observable<BaseJson> postDoDigForVolunteer(@Field("CurrentSystemID") int currentSystemId,
                                               @Field("NodeId") int nodeId,
                                               @Field("contentID") int contentId,
                                               @Field("type") int type);

}
