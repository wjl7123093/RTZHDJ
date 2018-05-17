package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.GradeRankEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 成绩排行列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-16
 * @update
 */
public interface GradeRankService {

    /**
     * 获取成绩排行列表
     * @return
     */
    @GET("GetTestResultList")
    Observable<BaseJson<GradeRankEntity>> getTestResultList(@Query("examinationID") int examinationID,
                                                            @Query("userID") int userID);

}
