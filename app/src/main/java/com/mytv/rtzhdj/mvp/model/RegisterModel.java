package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.api.cache.RegisterCache;
import com.mytv.rtzhdj.app.data.api.service.RegisterService;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.mvp.contract.RegisterContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<List<UserCategoryEntity>> getUserCategory(boolean update) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(RegisterService.class)
                .getUserCategory())
                .flatMap(new Function<Observable<List<UserCategoryEntity>>, ObservableSource<List<UserCategoryEntity>>>() {
                    @Override
                    public ObservableSource<List<UserCategoryEntity>> apply(@NonNull Observable<List<UserCategoryEntity>> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(RegisterCache.class)
                                .getUserCategory(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });
    }

    @Override
    public Observable<VerifyCodeEntity> getVerifyCode(String telNumber) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .getVerifyCode(telNumber);
    }

    @Override
    public Observable<UserRegisterEntity> getUserRegister(String moblie, String publishmentSystemId, String password) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .getRegister(moblie, publishmentSystemId, password);
    }
}