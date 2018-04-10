package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.NewsDetailCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.NewsDetailService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.mvp.contract.NewsDetailContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public NewsDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<NewsDetailEntity> getContent(String contentId, String modelType, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(NewsDetailService.class)
                .getContent(contentId, modelType))
                .flatMap(new Function<Observable<NewsDetailEntity>, ObservableSource<NewsDetailEntity>>() {
                    @Override
                    public ObservableSource<NewsDetailEntity> apply(@NonNull Observable<NewsDetailEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(NewsDetailCache.class)
                                .getContent(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}