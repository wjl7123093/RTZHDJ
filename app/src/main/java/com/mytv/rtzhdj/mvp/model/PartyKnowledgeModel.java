package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.PartyKnowledgeCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.PartyKnowledgeService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyKnowledgeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.mvp.contract.PartyKnowledgeContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class PartyKnowledgeModel extends BaseModel implements PartyKnowledgeContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PartyKnowledgeModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<PartyKnowledgeEntity> getPartyKnowledgeList(String nodeId, int count, int index, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(PartyKnowledgeService.class)
                .getPartyKnowledgeList(nodeId, count, index))
                .flatMap(new Function<Observable<PartyKnowledgeEntity>, ObservableSource<PartyKnowledgeEntity>>() {
                    @Override
                    public ObservableSource<PartyKnowledgeEntity> apply(@NonNull Observable<PartyKnowledgeEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(PartyKnowledgeCache.class)
                                .getPartyKnowledgeList(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}