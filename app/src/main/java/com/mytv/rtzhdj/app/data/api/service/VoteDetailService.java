package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 我要投票数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2018-4-11
 * @update 2018-5-5     update interface_name
 */
public interface VoteDetailService {

    /**
     * 获取我要投票数据
     * @return
     */
    @GET("getVoteOptionsList")
    Observable<BaseJson<List<VoteDetailEntity>>> getVoteOptionsList(@Query("id") int id,
                                                                    @Query("UserId") int userId);

}
