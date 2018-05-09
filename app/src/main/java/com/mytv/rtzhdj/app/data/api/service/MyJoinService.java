package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * 参加志愿服务 接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-9
 * @update
 */
public interface MyJoinService {

    /**
     * post 我要捐赠
     * @return
     */
    @Multipart
    @POST("postJoinVolunteerService")
    Observable<BaseJson> postJoinVolunteerService(@PartMap Map<String, RequestBody> params,
                                                  @Part List<MultipartBody.Part> parts);

}
