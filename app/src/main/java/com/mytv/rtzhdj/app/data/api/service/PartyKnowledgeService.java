package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyKnowledgeEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 党建知识列表 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface PartyKnowledgeService {

    /**
     * 获取党建知识列表数据
     * @return
     */
    @GET("getPartyKnowledgeList")
    Observable<PartyKnowledgeEntity> getPartyKnowledgeList(@Query("nodeId") String nodeId,
                                                           @Query("count") int count,
                                                           @Query("index") int index);

}
