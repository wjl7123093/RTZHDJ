package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.StudyCoursewareCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.StudyCoursewareService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.StudyCoursewareEntity;
import com.mytv.rtzhdj.mvp.contract.StudyCoursewareContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class StudyCoursewareModel extends BaseModel implements StudyCoursewareContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public StudyCoursewareModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<StudyCoursewareEntity> getCoursewareList(String typeId, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(StudyCoursewareService.class)
                .getCoursewareList(typeId))
                .flatMap(new Function<Observable<StudyCoursewareEntity>, ObservableSource<StudyCoursewareEntity>>() {
                    @Override
                    public ObservableSource<StudyCoursewareEntity> apply(@NonNull Observable<StudyCoursewareEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(StudyCoursewareCache.class)
                                .getCoursewareList(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}