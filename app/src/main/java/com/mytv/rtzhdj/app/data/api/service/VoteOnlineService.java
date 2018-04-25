package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteListEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 投票列表数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update
 */
public interface VoteOnlineService {

    /**
     * 获取投票列表数据
     * @return
     */
    @FormUrlEncoded
    @POST("postOnlineVoteList")
    Observable<BaseJson<List<VoteListEntity>>> getVoteList(@Field("TypeId") int typeId,
                                                           @Field("PageIndex") int pageIndex,
                                                           @Field("PageSize") int pageSize);

}
