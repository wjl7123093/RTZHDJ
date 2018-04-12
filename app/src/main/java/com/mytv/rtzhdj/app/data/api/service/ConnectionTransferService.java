package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 组织关系转接数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-12
 * @update
 */
public interface ConnectionTransferService {

    /**
     * 获取组织关系转接数据
     * @return
     */
    @GET("organizationalChange")
    Observable<String> getOrganizationalChange(@Query("publishmentsystemId") int publishmentsystemId,
                                                   @Query("reason") String reason,
                                                   @Query("userId") int userId);

}
