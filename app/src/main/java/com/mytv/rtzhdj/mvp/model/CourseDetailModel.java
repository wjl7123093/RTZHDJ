package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.CourseDetailService;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.mvp.contract.CourseDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class CourseDetailModel extends BaseModel implements CourseDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public CourseDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<CoursewareDetailEntity>> getCoursewareDetail(int id, boolean update) {
        return mRepositoryManager
                .obtainRetrofitService(CourseDetailService.class)
                .getCoursewareDetail(id);
    }

    @Override
    public Observable<BaseJson> postStudyClass(int userId, int nodeId, int contentId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(CourseDetailService.class)
                .postStudyClass(userId, nodeId, contentId);
    }
}