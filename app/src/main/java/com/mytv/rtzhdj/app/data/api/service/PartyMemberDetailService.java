package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 党员详情数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-27
 * @update
 */
public interface PartyMemberDetailService {

    /**
     * 获取党员详情数据
     * @return
     */
    @GET("getPartyMemberDetail")
    Observable<BaseJson<PartyMienEntity>> getPartyMemberDetail(@Query("MemberID") int memberId);

}
