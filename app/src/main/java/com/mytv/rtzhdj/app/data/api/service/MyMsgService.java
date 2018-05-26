package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyMsgEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 我的私信接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-2
 * @update
 */
public interface MyMsgService {

    /**
     * 我的私信
     * @return
     */
    @FormUrlEncoded
    @POST("postMyMessage")
    Observable<BaseJson<List<MyMsgEntity>>> postMyMessage(@Field("UserID") int userId,
                                                          @Field("MessageType") int messageType,
                                                          @Field("PageIndex") int pageIndex,
                                                          @Field("PageSize") int pageSize);

}
