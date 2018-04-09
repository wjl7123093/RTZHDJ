package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.ContentCache;
import com.mytv.rtzhdj.app.data.api.cache.NewsCache;
import com.mytv.rtzhdj.app.data.api.service.ContentService;
import com.mytv.rtzhdj.app.data.api.service.NewsService;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.mvp.contract.NewsContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class NewsModel extends BaseModel implements NewsContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public NewsModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<PartyColumnsEntity> getPartyColumns(String typedId, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(NewsService.class)
                .getPartyColumns(typedId))
                .flatMap(new Function<Observable<PartyColumnsEntity>, ObservableSource<PartyColumnsEntity>>() {
                    @Override
                    public ObservableSource<PartyColumnsEntity> apply(@NonNull Observable<PartyColumnsEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(NewsCache.class)
                                .getPartyColumns(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}