package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.cache.RegisterCache;
import com.mytv.rtzhdj.app.data.api.service.RegisterService;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.mvp.contract.RegisterContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
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
    public Observable<BaseJson<List<UserCategoryEntity>>> getUserCategory(boolean update) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .getUserCategory(); // 直接进行请求（通过 okhttp3 自带缓存进行数据缓存）

        /** 2. 使用 RxCache 作为缓存，但缓存的数据进行json转换时会出现 LinkedTreeMap cannot be cast to xxxbean.class 错误 */
        /*return mRepositoryManager.obtainCacheService(RegisterCache.class)
                .getUserCategory(mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .getUserCategory(), new DynamicKey(1), new EvictProvider(update))
                .map(resultReply -> resultReply.getData());*/

        /** 3. 使用 RxCache 作为缓存，同时用flatMap进行链式请求，
         * 但缓存的数据进行json转换时会出现 LinkedTreeMap cannot be cast to xxxbean.class 错误 */
        /*return Observable.just(mRepositoryManager
                .obtainRetrofitService(RegisterService.class)
                .getUserCategory())
                .flatMap(new Function<Observable<BaseJson<List<UserCategoryEntity>>>, ObservableSource<BaseJson<List<UserCategoryEntity>>>>() {
                    @Override
                    public ObservableSource<BaseJson<List<UserCategoryEntity>>> apply(@NonNull Observable<BaseJson<List<UserCategoryEntity>>> resultObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(RegisterCache.class)
                                .getUserCategory(resultObservable
                                        , new EvictProvider(update))
                                .map(resultReply -> resultReply.getData());
                    }
                });*/
    }

    @Override
    public Observable<BaseJson<List<StationEntity>>> postAllPublishmentSystem(boolean update) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .postAllPublishmentSystem();
    }

    @Override
    public Observable<BaseJson<VerifyCodeEntity>> getVerifyCode(String telNumber) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .getVerifyCode(telNumber);
    }

    @Override
    public Observable<UserRegisterEntity> getUserRegister(String moblie, String publishmentSystemId, String password) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .getRegister(moblie, publishmentSystemId, password);
    }
}