package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.mvp.contract.NewsDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.NewsDetailActivity;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;
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
public class NewsDetailPresenter extends BasePresenter<NewsDetailContract.Model, NewsDetailContract.View>
    implements NewsDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private NewsDetailActivity mActivity;

    @Inject
    public NewsDetailPresenter(NewsDetailContract.Model model, NewsDetailContract.View rootView
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
    public void setActivity(NewsDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void getNewsDetail(String url) {
        mRootView.loadWap(url);
    }

    @Override
    public void initWebview(WebView mWebView, WebProgressBar mWebProgressBar) {

        /** 以下属性基本全部设置，可以解决某些网页打不开的问题 */
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
//		mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage

        /**
         * 缓存模式说明:
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据
         */
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                mRootView.setWebviewProgress(newProgress);
            }
        });
        /**
         * 设置 WebViewClient 并重写 shouldOverrideUrlLoading() 方法，
         * 是为了防止加载 url 时自动跳转到浏览器打开
         */
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    @Override
    public void callMethodOfGetContent(int id, int nodeId, boolean update) {
        mModel.getContent(id, nodeId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<NewsDetailEntity>>transformObservable("getContent" + id,
                        new TypeToken<BaseJson<NewsDetailEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<NewsDetailEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<NewsDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<NewsDetailEntity> newsDetailEntity) {
                        Log.e(TAG, newsDetailEntity.getData().toString());

                        if (newsDetailEntity.isSuccess() && newsDetailEntity.getData() != null)
                            mRootView.loadData(newsDetailEntity.getData());
                    }
                });
    }

    @Override
    public void callMethodOfPostComment(int userId, int nodeId, int contentId, String commentInfo, boolean update) {
        mModel.postComment(userId, nodeId, contentId, commentInfo, update)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson postResult) {
                        Log.e(TAG, postResult.toString());

                        if (postResult.getStatus() == 200)
                            mRootView.showMessage("评论成功，审核中...");

                    }
                });
    }

    @Override
    public void callMethodOfPostDoDig(int nodeId, int contentId, int type, boolean update) {
        mModel.postDoDig(nodeId, contentId, type, update)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson postResult) {
                        Log.e(TAG, postResult.toString());

                        if (postResult.isSuccess())
                            mRootView.changeDigsStatus(type);

                    }
                });
    }
}
