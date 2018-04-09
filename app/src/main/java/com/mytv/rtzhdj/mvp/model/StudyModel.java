package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.StudyCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.StudyService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.MyStudyEntity;
import com.mytv.rtzhdj.mvp.contract.StudyContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class StudyModel extends BaseModel implements StudyContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public StudyModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<MyStudyEntity> getMyStudy(int userId, int count, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(StudyService.class)
                .getMyStudyData(userId, count))
                .flatMap(new Function<Observable<MyStudyEntity>, ObservableSource<MyStudyEntity>>() {
                    @Override
                    public ObservableSource<MyStudyEntity> apply(@NonNull Observable<MyStudyEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(StudyCache.class)
                                .getMyStudyData(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}