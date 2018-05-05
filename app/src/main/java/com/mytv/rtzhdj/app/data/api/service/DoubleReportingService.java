package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 双报到 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-5
 * @update
 */
public interface DoubleReportingService {

    /**
     * post 双报到
     * @return
     */
    @FormUrlEncoded
    @POST("postPersonalReach")
    Observable<BaseJson> postPersonalReach(@Field("UserId") int userId,
                                           @Field("PublishmentSystemId") int publishmentSystemId);

}
