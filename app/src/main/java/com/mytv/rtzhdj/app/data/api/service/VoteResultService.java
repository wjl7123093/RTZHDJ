package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 投票结果接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-3
 * @update
 */
public interface VoteResultService {

    /**
     * 投票结果
     * @return
     */
    @FormUrlEncoded
    @POST("postOnlineVoteResult")
    Observable<BaseJson<List<VoteDetailEntity>>> postOnlineVoteResult(@Field("id") int id);

}
