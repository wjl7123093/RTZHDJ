package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.VoteOnlineCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.VoteOnlineService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteListEntity;
import com.mytv.rtzhdj.mvp.contract.VoteOnlineContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class VoteOnlineModel extends BaseModel implements VoteOnlineContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VoteOnlineModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<VoteListEntity> getVoteList(int typeId, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(VoteOnlineService.class)
                .getVoteList(typeId))
                .flatMap(new Function<Observable<VoteListEntity>, ObservableSource<VoteListEntity>>() {
                    @Override
                    public ObservableSource<VoteListEntity> apply(@NonNull Observable<VoteListEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(VoteOnlineCache.class)
                                .getVoteList(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}