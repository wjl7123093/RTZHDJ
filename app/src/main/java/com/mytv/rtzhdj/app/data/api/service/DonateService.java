package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * 我要捐赠 接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-7
 * @update
 */
public interface DonateService {

    /**
     * post 我要捐赠
     * @return
     */
    @Multipart
    @POST("postDonateSubmit")
    Observable<BaseJson> postDonateSubmit(@PartMap Map<String, RequestBody> params,
                                          @Part List<MultipartBody.Part> parts);

}
