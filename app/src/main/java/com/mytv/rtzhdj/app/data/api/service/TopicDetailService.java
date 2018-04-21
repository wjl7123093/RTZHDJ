package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.SpecialColumnsEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 专题详情接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-8
 * @update
 */
public interface TopicDetailService {

    /**
     * 获取专题二级栏目数据
     * @return
     */
    @GET("getSpecialClass")
    Observable<BaseJson<SpecialColumnsEntity>> getSpecialClass(@Query("NodeId") int nodeId);

}
