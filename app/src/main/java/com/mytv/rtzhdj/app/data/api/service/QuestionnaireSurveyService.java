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
 * 在线问卷问题列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-22
 * @update
 */
public interface QuestionnaireSurveyService {

    /**
     * 获取在线问卷问题列表
     * @return
     */
    @GET("GetSurveyDetailList")
    Observable<BaseJson<List<QuestionEntity>>> getSurveyDetailList(@Query("OnlineSurveyId") int onlineSurveyId);

    /**
     * 提交在线问卷结果
     * @return
     */
    @FormUrlEncoded
    @POST("PostSurveyInfo")
    Observable<BaseJson> postSurveyInfo(@Field("UserID") int userID,
                                        @Field("AnswerString") String answerString);

}
