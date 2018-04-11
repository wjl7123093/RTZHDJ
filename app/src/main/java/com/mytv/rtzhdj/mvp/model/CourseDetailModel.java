package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.CourseDetailCache;
import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.service.CourseDetailService;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.mvp.contract.CourseDetailContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


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
    public Observable<CoursewareDetailEntity> getCoursewareDetail(int id, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseDetailService.class)
                .getCoursewareDetail(id))
                .flatMap(new Function<Observable<CoursewareDetailEntity>, ObservableSource<CoursewareDetailEntity>>() {
                    @Override
                    public ObservableSource<CoursewareDetailEntity> apply(@NonNull Observable<CoursewareDetailEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CourseDetailCache.class)
                                .getCoursewareDetail(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}