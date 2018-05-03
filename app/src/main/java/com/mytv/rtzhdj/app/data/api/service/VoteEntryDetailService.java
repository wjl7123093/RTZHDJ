package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 投票作品详情接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-3
 * @update
 */
public interface VoteEntryDetailService {

    /**
     * 投票作品详情
     * @return
     */
    @FormUrlEncoded
    @POST("postOnlineVoteDetails")
    Observable<BaseJson<VoteEntrylEntity>> postOnlineVoteDetails(@Field("id") int id);

}
