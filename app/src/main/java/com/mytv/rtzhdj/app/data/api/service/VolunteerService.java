package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 志愿服务列表 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface VolunteerService {

    /**
     * 获取志愿服务列表数据
     * @return
     */
    @GET("getVoluntaryserviceList")
    Observable<BaseJson<List<VoluteerServiceEntity>>> getVoluntaryserviceList(@Query("TypeId") int typeId,
                                                                              @Query("PageIndex") int pageIndex,
                                                                              @Query("PageSize") int pageSize);

}
