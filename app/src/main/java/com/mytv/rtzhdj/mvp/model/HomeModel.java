package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.RegisterCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.RegisterService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.mvp.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<HomeEntity> getHomeData(int curUserId, int pageSize, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getHomeData(curUserId, pageSize))
                .flatMap(new Function<Observable<HomeEntity>, ObservableSource<HomeEntity>>() {
                    @Override
                    public ObservableSource<HomeEntity> apply(@NonNull Observable<HomeEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(HomeCache.class)
                                .getHomeData(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}