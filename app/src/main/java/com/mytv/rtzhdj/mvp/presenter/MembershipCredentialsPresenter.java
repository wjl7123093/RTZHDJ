package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MembershipEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.mvp.contract.MembershipCredentialsContract;
import com.mytv.rtzhdj.mvp.ui.activity.MembershipCredentialsActivity;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import org.raphets.roundimageview.RoundImageView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MembershipCredentialsPresenter extends BasePresenter<MembershipCredentialsContract.Model, MembershipCredentialsContract.View>
    implements MembershipCredentialsContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MembershipCredentialsActivity mActivity;

    @Inject
    public MembershipCredentialsPresenter(MembershipCredentialsContract.Model model, MembershipCredentialsContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void setActivity(MembershipCredentialsActivity activity) {
        mActivity = activity;
    }

    @Override
    public void initHeader(PartyMemberEntity partyMemberEntity, RoundImageView ivHeader,
                           TextView tvName, TextView tvDuty, TextView tvPartyBranch) {
        tvName.setText("姓名: " + partyMemberEntity.getUserName());
        tvDuty.setText("职务: " + partyMemberEntity.getDuty());
        tvPartyBranch.setText("所属支部: " + partyMemberEntity.getParty_branch());

        mImageLoader.loadImage(mActivity,
                ImageConfigImpl
                        .builder()
                        .errorPic(R.mipmap.ic_error)
                        .placeholder(R.mipmap.ic_placeholder)
                        .url(partyMemberEntity.getPhotourl())
                        .imageView(ivHeader)
                        .build());
    }

    @Override
    public RecyclerView initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置item间距
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 10)));

        return recyclerView;
    }

    @Override
    public void callMethodOfGetUserTransList(boolean refresh) {
        mModel.getUserTransList(refresh)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<MembershipEntity>>>transformObservable("MembershipEntity",
                        new TypeToken<BaseJson<List<MembershipEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<MembershipEntity>>>())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    // Action onFinally
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<MembershipEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<MembershipEntity>> membershipList) {
                        Log.e("TAG", membershipList.toString());

//                        mRootView.showPickerView(stationList.getData());
                        if (membershipList.isSuccess() && membershipList.getData() != null)
                            mRootView.loadData(membershipList.getData());
                    }
                });
    }
}
