package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.VoteDetailCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.VoteDetailService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;
import com.mytv.rtzhdj.mvp.contract.VoteDetailContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class VoteDetailModel extends BaseModel implements VoteDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VoteDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<VoteDetailEntity> getMyVoteDetail(int id, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(VoteDetailService.class)
                .getMyVoteDetail(id))
                .flatMap(new Function<Observable<VoteDetailEntity>, ObservableSource<VoteDetailEntity>>() {
                    @Override
                    public ObservableSource<VoteDetailEntity> apply(@NonNull Observable<VoteDetailEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(VoteDetailCache.class)
                                .getMyVoteDetail(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}