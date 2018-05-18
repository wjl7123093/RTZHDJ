package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 问题列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-15
 * @update 2017-5-18    新增 postTestInfo 接口
 */
public interface QuestionnaireService {

    /**
     * 获取效果评测问题列表
     * @return
     */
    @GET("getTestInfo")
    Observable<BaseJson<List<QuestionEntity>>> getTestInfo(@Query("examinationID") int examinationID);

    /**
     * 提交效果评测结果
     * @return
     */
    @FormUrlEncoded
    @POST("PostTestInfo")
    Observable<BaseJson> postTestInfo(@Field("UserID") int userID,
                                      @Field("ExaminationID") int examinationID,
                                      @Field("Score") int score);

}
