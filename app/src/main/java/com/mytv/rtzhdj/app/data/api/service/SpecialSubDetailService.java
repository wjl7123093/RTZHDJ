package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 专题二级列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-17
 * @update
 */
public interface SpecialSubDetailService {

    /**
     * 获取专题二级列表
     * @return
     */
    @GET("getPartySpecialList")
    Observable<BaseJson<List<NewsDetailEntity>>> getPartySpecialList(@Query("NodeId") int nodeId);

}
