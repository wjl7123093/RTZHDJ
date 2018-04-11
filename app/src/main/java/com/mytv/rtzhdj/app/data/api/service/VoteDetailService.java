package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 我要投票数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update
 */
public interface VoteDetailService {

    /**
     * 获取我要投票数据
     * @return
     */
    @GET("/getMyVoteDetail")
    Observable<VoteDetailEntity> getMyVoteDetail(@Query("id") int id);

}
