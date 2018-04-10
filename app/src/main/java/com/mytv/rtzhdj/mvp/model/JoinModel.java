package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.JoinCache;
import com.mytv.rtzhdj.app.data.api.service.JoinService;
import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;
import com.mytv.rtzhdj.mvp.contract.JoinContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class JoinModel extends BaseModel implements JoinContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public JoinModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<MyJoinEntity> getMyPartIn(int userId, int count, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(JoinService.class)
                .getMyPartIn(userId, count))
                .flatMap(new Function<Observable<MyJoinEntity>, ObservableSource<MyJoinEntity>>() {
                    @Override
                    public ObservableSource<MyJoinEntity> apply(@NonNull Observable<MyJoinEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(JoinCache.class)
                                .getMyPartIn(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}