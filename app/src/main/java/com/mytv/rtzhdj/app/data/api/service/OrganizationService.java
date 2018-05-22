package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.OrganizationEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 评论列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-22
 * @update
 */
public interface OrganizationService {

    /**
     * 获取组织活动列表
     * @return
     */
    @GET("getOrganizationActivityList")
    Observable<BaseJson<List<OrganizationEntity>>> getOrganizationActivityList(@Query("publishmentSystemId") int publishmentSystemId,
                                                                               @Query("MeettingTypeId") int meettingTypeId);

}
