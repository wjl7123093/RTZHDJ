package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.cache.ContentCache;
import com.mytv.rtzhdj.app.data.api.service.ContentService;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
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
    public Observable<BaseJson<PartyRecommendEntity>> getPartyRecommend(int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(ContentService.class)
                .getPartyRecommend(pageIndex, pageSize);
    }

    @Override
    public Observable<PartySubNewsEntity> getPartySubList(int nodeId, String typedId, int count, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ContentService.class)
                .getPartySubList(nodeId, typedId, count))
                .flatMap(new Function<Observable<PartySubNewsEntity>, ObservableSource<PartySubNewsEntity>>() {
                    @Override
                    public ObservableSource<PartySubNewsEntity> apply(@NonNull Observable<PartySubNewsEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(ContentCache.class)
                                .getPartySubNews(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}