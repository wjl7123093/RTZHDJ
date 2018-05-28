package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.StudyRecordEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 学习记录数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-10
 * @update
 */
public interface StudyRecordService {

    /**
     * 获取学习记录数据
     * @return
     */
    @GET("getLearningRecords")
    Observable<BaseJson<List<StudyRecordEntity>>> getLearningRecords(@Query("userId") int curUserId);

}
