package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MineEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 我的积分等数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-28
 * @update
 */
public interface MineService {

    /**
     * 获取我的积分等数据
     * @return
     */
    @GET("GetUserPartMessage")
    Observable<BaseJson<MineEntity>> getUserPartMessage(@Query("UserId") int userId);

}
