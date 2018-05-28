package com.mytv.rtzhdj.app.data.api.service;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.TeacherEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 师资库列表 数据接口
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2017-5-28
 * @update
 */
public interface TeacherListService {

    /**
     * 获取师资库列表数据
     * @return
     */
    @GET("GetTeacherList ")
    Observable<BaseJson<List<TeacherEntity>>> getTeacherList (@Query("NodeId") int nodeId,
                                                              @Query("PageIndex") int pageIndex,
                                                              @Query("PageSize") int pageSize);

}
