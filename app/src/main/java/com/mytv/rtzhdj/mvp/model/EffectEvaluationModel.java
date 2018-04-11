package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.EffectEvaluationCache;
import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.service.EffectEvaluationService;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.entity.EffectEvaluationEntity;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.mvp.contract.EffectEvaluationContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class EffectEvaluationModel extends BaseModel implements EffectEvaluationContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public EffectEvaluationModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<EffectEvaluationEntity> getTestList(int userId, int typeId, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(EffectEvaluationService.class)
                .getTestList(userId, typeId))
                .flatMap(new Function<Observable<EffectEvaluationEntity>, ObservableSource<EffectEvaluationEntity>>() {
                    @Override
                    public ObservableSource<EffectEvaluationEntity> apply(@NonNull Observable<EffectEvaluationEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(EffectEvaluationCache.class)
                                .getTestList(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}