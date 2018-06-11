package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 评论列表接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-4-7
 * @update
 */
public interface CommentService {

    /**
     * 获取评论列表
     * @return
     */
    @GET("getCommentList")
    Observable<BaseJson<List<CommentEntity>>> getCommentList(@Query("CurrentSystemID") int currentSystemId,
                                                             @Query("NodeId") int nodeId,
                                                             @Query("ContentID") int contentId);

}
