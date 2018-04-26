package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.EffectEvaluationEntity;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 效果评测列表数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-11
 * @update
 */
public interface EffectEvaluationService {

    /**
     * 获取效果评测列表数据
     * @return
     */
    @GET("getTestList")
    Observable<BaseJson<List<EffectEvaluationEntity>>> getTestList(@Query("UserId") int userId,
                                                                   @Query("TestState") int testState);

}
