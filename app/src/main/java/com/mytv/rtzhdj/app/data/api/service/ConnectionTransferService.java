package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.StationEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 组织关系转接数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2018-4-12
 * @update 2018-4-27    新增 获取占点集合数据 接口
 */
public interface ConnectionTransferService {

    /**
     * 获取组织关系转接数据
     * @return
     */
    @FormUrlEncoded
    @POST("postOganizationalChange")
    Observable<BaseJson> getOrganizationalChange(@Field("PublishmentSystemId") int publishmentsystemId,
                                                 @Field("Reason") String reason,
                                                 @Field("UserId") int userId);

    /**
     * 获取站点集合数据
     * @return
     */
    @POST("postAllPublishmentSystem")
    Observable<BaseJson<List<StationEntity>>> postAllPublishmentSystem();

}
