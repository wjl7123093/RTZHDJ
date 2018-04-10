package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.VolunteerServiceDetailCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.VolunteerServiceDetailService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceDetailContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class VolunteerServiceDetailModel extends BaseModel implements VolunteerServiceDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VolunteerServiceDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<VolunteerDetailEntity> getVolunteerServiceDetail(String id, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(VolunteerServiceDetailService.class)
                .getVolunteerServiceDetail(id))
                .flatMap(new Function<Observable<VolunteerDetailEntity>, ObservableSource<VolunteerDetailEntity>>() {
                    @Override
                    public ObservableSource<VolunteerDetailEntity> apply(@NonNull Observable<VolunteerDetailEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(VolunteerServiceDetailCache.class)
                                .getVolunteerServiceDetail(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}