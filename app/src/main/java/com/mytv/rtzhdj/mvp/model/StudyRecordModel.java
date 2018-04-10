package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.StudyRecordCache;
import com.mytv.rtzhdj.app.data.api.service.StudyRecordService;
import com.mytv.rtzhdj.app.data.entity.StudyRecordEntity;
import com.mytv.rtzhdj.mvp.contract.StudyRecordContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class StudyRecordModel extends BaseModel implements StudyRecordContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public StudyRecordModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<StudyRecordEntity> getLearningRecords(int userId, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(StudyRecordService.class)
                .getLearningRecords(userId))
                .flatMap(new Function<Observable<StudyRecordEntity>, ObservableSource<StudyRecordEntity>>() {
                    @Override
                    public ObservableSource<StudyRecordEntity> apply(@NonNull Observable<StudyRecordEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(StudyRecordCache.class)
                                .getLearningRecords(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}