package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 党员信息列表数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-12
 * @update
 */
public interface PartyMemberService {

    /**
     * 获取党员信息列表数据
     * @return
     */
    @GET("getPartyMember")
    Observable<BaseJson<List<PartyMemberEntity>>> getPartyMember(@Query("PublishmentSystemId") int publishmentSystemId);

    /**
     * 获取党员风采数据
     * @return
     */
    @GET("getPartymembermien")
    Observable<BaseJson<List<PartyMienEntity>>> getPartymembermien(@Query("PublishmentSystemId") int publishmentSystemId,
                                                                   @Query("PageIndex") int pageIndex,
                                                                   @Query("PageSize") int pageSize);

}
