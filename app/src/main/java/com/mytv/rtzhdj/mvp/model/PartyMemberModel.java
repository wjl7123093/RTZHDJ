package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.cache.PartyMemberCache;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.PartyMemberService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.mvp.contract.PartyMemberContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class PartyMemberModel extends BaseModel implements PartyMemberContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PartyMemberModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<PartyMemberEntity> getPartyMember(int publishmentSystemId, boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(PartyMemberService.class)
                .getPartyMember(publishmentSystemId))
                .flatMap(new Function<Observable<PartyMemberEntity>, ObservableSource<PartyMemberEntity>>() {
                    @Override
                    public ObservableSource<PartyMemberEntity> apply(@NonNull Observable<PartyMemberEntity> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(PartyMemberCache.class)
                                .getPartyMember(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }
}