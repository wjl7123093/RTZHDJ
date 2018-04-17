package com.mytv.rtzhdj.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.mytv.rtzhdj.BuildConfig;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.api.intercreptor.HttpLoggingInterceptor;
import com.mytv.rtzhdj.app.data.api.intercreptor.LoggingInterceptor;
//import com.mytv.rtzhdj.app.utils.fastjson.FastJsonConverterFactory;
import com.mytv.rtzhdj.app.utils.NetworkUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import static com.mytv.rtzhdj.app.utils.CrashHandler.TAG;

/**
 * app的全局配置信息在此配置,需要将此实现类声明到AndroidManifest中
 *
 * @author Fred_W
 * @version 1.0.0
 *
 * @crdate 2017-1-18
 * @update 2018-1-29    删除相关参数，保证 Glide 正常加载图片
 *         2018-4-16    新增 okhttp 缓存配置
 */
public class GlobalConfiguration implements ConfigModule {

//    private RTZHDJApplication mApplication;

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        //使用builder可以为框架配置一些配置信息
        builder.baseurl(Api.APP_DOMAIN)
                .addInterceptor(new LoggingInterceptor());
//                .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
////                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
//                    retrofitBuilder.client(getOkHttpClient(context1, true, true));  // 配置okhttpclient，使用okhttp自带缓存
//                });

        /**
         * 配置以下参数后，会导致 Glide 无法加载图片
         */
                /*.cacheFile(new File("cache"))
                .gsonConfiguration((context12, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
//                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
                })
                .okhttpConfiguration((context1, okhttpBuilder) -> {//这里可以自己自定义配置Okhttp的参数
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                }).rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                });*/

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {


            @Override
            public void attachBaseContext(Context base) {

            }

            @Override
            public void onCreate(Application application) {

                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                }
                //leakCanary内存泄露检查
                ((App) application).getAppComponent().extras().put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);

                //ARouter初始化
                if (BuildConfig.LOG_DEBUG) {
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                }
                ARouter.init(application); // 尽可能早，推荐在Application中初始化
//                GreenDaoHelper.initDatabase(application);

            }

            @Override
            public void onTerminate(Application application) {

            }
        });
    }


    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向Activity的生命周期中注入一些自定义逻辑
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.w(activity + " - onActivityCreated");
                /**
                 * 在 onActivityCreated() 里调用 findViewById() 返回的永远为 null
                 * 故将该段代码放至 onActivityStarted() 里
                 */
                // ......
//                mApplication = (CCApplication) activity.getApplication();
            }

            @Override
            public void onActivityStarted(Activity activity) {
                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                if (activity.findViewById(R.id.toolbar) != null) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                            activity.getActionBar().setDisplayShowTitleEnabled(false);
                        }
                    }
                }
                if (activity.findViewById(R.id.toolbar_title) != null) {
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }
                if (activity.findViewById(R.id.toolbar_back) != null) {
                    activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                        activity.onBackPressed();
                    });
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //向Fragment的生命周期中注入一些自定义逻辑
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                ((RefWatcher) ((App) f.getActivity().getApplication()).getAppComponent().extras().get(RefWatcher.class.getName())).watch(f);
            }
        });
    }

    private static final int DEFAULT_TIMEOUT = 5;
    private static final int DEFAULT_HTTP_CACHE_SIZE = 10 * 1024 * 1024;
    /**
     * 缓存策略
     *
     * @param context
     * @param isAllowCache 是否允许使用缓存策略
     * @param cacheMethod  false:有网和没有网都是先读缓存 true:离线可以缓存，在线就获取最新数据 default=false
     * @return
     */
    private OkHttpClient getOkHttpClient(final Context context, boolean isAllowCache, boolean cacheMethod) {
        /**
         * 获取缓存
         */
        Interceptor baseInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                /*Request request = chain.request();
                if (!NetworkUtils.isNetworkAvailable(context)) {
                    *//**
                     * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
                     *//*
                    int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
                    CacheControl tempCacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(maxStale, TimeUnit.SECONDS)
                            .build();
                    request = request.newBuilder()
                            .cacheControl(tempCacheControl)
                            .build();
                    Log.i(TAG, "intercept:no network ");
                }
                return chain.proceed(request);*/

                Request request = chain.request();
                Response response = chain.proceed(request);

                if (NetworkUtils.isNetworkAvailable(context)) {
                    int maxAge = 60*60*24*2;//缓存失效时间，单位为秒
                    return response.newBuilder()
                            .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .header("Cache-Control", "public ,max-age=" + maxAge)
                            .build();
                }
                return response;

            }
        };
        //只有 网络拦截器环节 才会写入缓存写入缓存,在有网络的时候 设置缓存时间
        HttpLoggingInterceptor rewriteCacheControlInterceptor = new HttpLoggingInterceptor(isAllowCache);
        //设置缓存路径 内置存储
        //File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        //外部存储
        File httpCacheDirectory = new File(context.getExternalCacheDir(), "responses");
        //设置缓存 10M
        int cacheSize = DEFAULT_HTTP_CACHE_SIZE;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
//        if (isAllowCache && cacheMethod) {
//            builder.addNetworkInterceptor(baseInterceptor);
//        }
        builder.addNetworkInterceptor(baseInterceptor);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }


}
