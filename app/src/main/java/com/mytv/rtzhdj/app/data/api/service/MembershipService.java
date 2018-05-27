package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MembershipEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 组织关系接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-24
 * @update
 */
public interface MembershipService {

    /**
     * 获取组织关系
     * @return
     */
    @GET("getUserTransList")
    Observable<BaseJson<List<MembershipEntity>>> getUserTransList(@Query("UserID") int userId);
}
