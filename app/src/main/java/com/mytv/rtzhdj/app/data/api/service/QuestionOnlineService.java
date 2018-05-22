package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.QuestionOnlineEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 在线问卷列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-15
 * @update
 */
public interface QuestionOnlineService {

    /**
     * 获取在线问卷列表
     * @return
     */
    @GET("GetSurveyList")
    Observable<BaseJson<List<QuestionOnlineEntity>>> GetSurveyList(@Query("PublishmentSystemId") int publishmentSystemId,
                                                                   @Query("Type") int type);

}
