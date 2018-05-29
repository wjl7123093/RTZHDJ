package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.Constant;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.UserDetailEntity;
import com.mytv.rtzhdj.mvp.contract.SettingsContract;
import com.mytv.rtzhdj.mvp.ui.activity.SettingsActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class SettingsPresenter extends BasePresenter<SettingsContract.Model, SettingsContract.View>
    implements SettingsContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private SettingsActivity mActivity;

    @Inject
    public SettingsPresenter(SettingsContract.Model model, SettingsContract.View rootView
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
    public void setActivity(SettingsActivity activity) {
        mActivity = activity;
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mActivity);
//        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    @Override
    public BaseDelegateAdapter initHeader1(String url) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_settings_header1,
                1, Constant.viewTypeSettings.typeHeader1) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                if (!TextUtils.isEmpty(DataHelper.getStringSF(mActivity, SharepreferenceKey.KEY_LOGIN_HEADER_URL)))
                    mImageLoader.loadImage(mActivity,
                            ImageConfigImpl
                                    .builder()
                                    .errorPic(R.mipmap.ic_error)
                                    .placeholder(R.mipmap.ic_placeholder)
                                    .url(DataHelper.getStringSF(mActivity, SharepreferenceKey.KEY_LOGIN_HEADER_URL))
                                    .imageView(holder.getView(R.id.iv_header))
                                    .build());
            }
        };
    }

    @Override
    public BaseDelegateAdapter initHeader2(String url) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_settings_header2,
                1, Constant.viewTypeSettings.typeHeader2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                if (!TextUtils.isEmpty(DataHelper.getStringSF(mActivity, SharepreferenceKey.KEY_LOGIN_HEADER_URL)))
                    mImageLoader.loadImage(mActivity,
                            ImageConfigImpl
                                    .builder()
                                    .errorPic(R.mipmap.ic_error)
                                    .placeholder(R.mipmap.ic_placeholder)
                                    .url(DataHelper.getStringSF(mActivity, SharepreferenceKey.KEY_LOGIN_HEADER_URL))
                                    .imageView(holder.getView(R.id.iv_header))
                                    .build());
            }
        };
    }

    @Override
    public BaseDelegateAdapter initInfo1(String title, String content) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_settings_info1,
                1, Constant.viewTypeSettings.typeInfo1) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_info, title + content);

                holder.getView(R.id.ll_container).setOnClickListener(view -> {
                    if (title.contains("退出")) {
                        ArmsUtils.exitApp();
                        ARouter.getInstance().build(ARoutePath.PATH_LOGIN).navigation();
                        DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_IS_LOGIN, 0);    // 退出登录
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initInfo2(String type, String title, String content, String hint) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_settings_info2,
                1, Constant.viewTypeSettings.typeInfo2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_content, content);
                ((TextView) holder.getView(R.id.tv_content)).setHint(hint);

                holder.getView(R.id.rl_container).setOnClickListener(view -> {
                    if (type.equals("pwd"))
                        ARouter.getInstance().build(ARoutePath.PATH_UPDATE_PWD).navigation();
                    else if (type.equals("info"))
                        ARouter.getInstance().build(ARoutePath.PATH_UPDATE_INFO)
                                .withString("hint", "请输入" + hint).navigation();
                    else if (type.equals("webview"))
                        ARouter.getInstance().build(ARoutePath.PATH_WEBVIEW)
                                .withString("title", title).navigation();
                    else if (type.equals("feedback"))
                        ARouter.getInstance().build(ARoutePath.PATH_FEEDBACK).navigation();
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initMobilePhone(String mobile, boolean isOpen) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_settings_mobilephone,
                1, Constant.viewTypeSettings.typeInfo2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_mobile_phone, "绑定的手机号  " + mobile.replace(mobile.substring(3, 7), "****"));
                holder.getView(R.id.tv_change_phone).setOnClickListener(view -> {
                    ARouter.getInstance().build(ARoutePath.PATH_BINDING_MOBILE).navigation();
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initOtherContacts(String title, String content, String hint) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_settings_info3,
                1, Constant.viewTypeSettings.typeInfo3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_content, content);
                ((TextView) holder.getView(R.id.tv_content)).setHint(hint);

                holder.getView(R.id.rl_container).setOnClickListener(view -> {
                    ARouter.getInstance().build(ARoutePath.PATH_UPDATE_INFO)
                            .withString("hint", "请输入" + hint).navigation();
                });
            }
        };
    }

    @Override
    public void callMethodOfGetUserDetail(int userId, boolean update) {
        mModel.getUserDetail(userId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<UserDetailEntity>>transformObservable("getUserDetail" + userId,
                        new TypeToken<BaseJson<UserDetailEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<UserDetailEntity>>())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<UserDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<UserDetailEntity> userDetailEntity) {
                        Log.e(TAG, userDetailEntity.toString());

                        if (userDetailEntity.isSuccess() && userDetailEntity.getData() != null) {
                            DataHelper.saveDeviceData(mActivity, SharepreferenceKey.KEY_LOGIN_USER_DETAIL, userDetailEntity.getData());
                            mRootView.loadData(userDetailEntity.getData(), 0);
                        }
                    }
                });
    }
}
