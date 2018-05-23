package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.mvp.contract.CourseDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.CourseDetailActivity;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CourseDetailPresenter extends BasePresenter<CourseDetailContract.Model, CourseDetailContract.View>
    implements CourseDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private CourseDetailActivity mActivity;

    @Inject
    public CourseDetailPresenter(CourseDetailContract.Model model, CourseDetailContract.View rootView
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
    public void setActivity(CourseDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void getCourseDetail(String url) {
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
    public void callMethodOfGetCoursewareDetail(int id, boolean update) {
        mModel.getCoursewareDetail(id, update)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<CoursewareDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<CoursewareDetailEntity> coursewareDetailEntity) {
                        Log.e(TAG, coursewareDetailEntity.toString());

                        if (coursewareDetailEntity.isSuccess())
                            mRootView.showData(coursewareDetailEntity.getData());

                    }
                });
    }
}
