package com.mytv.rtzhdj.app.base;

import android.content.Context;
import android.graphics.Bitmap;

import com.jess.arms.base.BaseApplication;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.utils.CrashHandler;
import com.mytv.rtzhdj.app.utils.datetime.DynamicTimeFormat;
import com.mytv.rtzhdj.app.utils.sonic.SonicRuntimeImpl;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.diskconverter.GsonDiskConverter;

import java.io.File;

//import org.xutils.x;

/**
 * 全局 Application
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-1-22 修改了 SmartRefreshLayout 的 Header 和 Footer 样式（贝塞尔 -> 经典）
 */
public class RTZHDJApplication extends BaseApplication {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //默认是 贝塞尔雷达Header, 指定为 经典Header
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于%s"));//.setTimeFormat(new DynamicTimeFormat(""));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //默认是 BallPulseFooter, 指定为 经典Footer
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private static Context mContext;
    public static DisplayImageOptions mOptions;
    public static RxCache rxCache;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initImageLoader(this);

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(this), new SonicConfig.Builder().build());
        }

        // 全局异常捕捉
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());

        // 初始化 xutils3
//        x.Ext.init(this);
//        x.Ext.setDebug(false); //输出debug日志，开启会影响性能

        rxCache = new RxCache.Builder()
                .appVersion(1)//当版本号改变,缓存路径下存储的所有数据都会被清除掉
                .diskDir(new File(getCacheDir().getPath() + File.separator + "data-cache"))
                .diskConverter(new GsonDiskConverter())//支持Serializable、Json(GsonDiskConverter)
                .memoryMax(2*1024*1024)
                .diskMax(20*1024*1024)
                .build();

    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化ImageLoader(Universal-image-loader)
     */
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(200 * 1024 * 1024) // 200 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);


        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)   //加载过程中
                .showImageForEmptyUri(R.mipmap.ic_launcher) //uri为空时
                .showImageOnFail(R.mipmap.ic_launcher)      //加载失败时
                .cacheOnDisk(true)
                .cacheInMemory(true)                             //允许cache在内存和磁盘中
                .bitmapConfig(Bitmap.Config.RGB_565)             //图片压缩质量参数
                .build();
    }

}
