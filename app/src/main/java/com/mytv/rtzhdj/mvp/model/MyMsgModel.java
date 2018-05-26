package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.MyMsgService;
import com.mytv.rtzhdj.app.data.entity.MyMsgEntity;
import com.mytv.rtzhdj.mvp.contract.MyMsgContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class MyMsgModel extends BaseModel implements MyMsgContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MyMsgModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<MyMsgEntity>>> postMyMessage(int userId, int messageType,
                                                                 int pageIndex, int pageSize, boolean udpate) {
        return mRepositoryManager.obtainRetrofitService(MyMsgService.class)
                .postMyMessage(userId, messageType, pageIndex, pageSize);
    }
}