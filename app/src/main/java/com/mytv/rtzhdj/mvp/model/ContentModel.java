package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.ContentCache;
import com.mytv.rtzhdj.app.data.api.service.ContentService;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.mvp.contract.ContentContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class ContentModel extends BaseModel implements ContentContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ContentModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<PartyRecommendEntity> getPartyRecommend(String typedId, int count, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ContentService.class)
                .getPartyRecommend(typedId, count))
                .flatMap(new Function<Observable<PartyRecommendEntity>, ObservableSource<PartyRecommendEntity>>() {
                    @Override
                    public ObservableSource<PartyRecommendEntity> apply(@NonNull Observable<PartyRecommendEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(ContentCache.class)
                                .getPartyRecommend(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}