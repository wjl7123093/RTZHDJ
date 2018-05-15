package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.PartyLiveEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 党建直播接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-15
 * @update
 */
public interface NewsVideoDetailService {

    /**
     * 获取党建直播
     * @return
     */
    @GET("GetPartyLiveInfo")
    Observable<BaseJson<PartyLiveEntity>> GetPartyLiveInfo();

}
