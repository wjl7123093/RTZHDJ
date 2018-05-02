package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
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
 * 发送私信接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-2
 * @update
 */
public interface SendMsgService {

    /**
     * 发送私信
     * @return
     */
    @FormUrlEncoded
    @POST("postSendMessage")
    Observable<BaseJson> postSendMessage(@Field("UserID") int userId,
                                         @Field("ReceiveID") int receiveId,
                                         @Field("Topic") String topic,
                                         @Field("MainMessage") String msg);

}
