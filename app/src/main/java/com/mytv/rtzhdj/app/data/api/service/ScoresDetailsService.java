package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.ScoresDetailsEntity;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 积分明细 接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-10
 * @update
 */
public interface ScoresDetailsService {

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST("postMyScore")
    Observable<BaseJson<ScoresDetailsEntity>> postMyScore(@Field("UserID") int userId);

}
