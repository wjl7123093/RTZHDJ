package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.ReportCommunityEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 报到社区列表 接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-7
 * @update
 */
public interface ReportCommunityService {

    /**
     * 获取 报到社区列表
     * @return
     */
    @GET("GetPartOrgList")
    Observable<BaseJson<List<ReportCommunityEntity>>> getPartOrgList(@Query("UserID") int userId);

}
