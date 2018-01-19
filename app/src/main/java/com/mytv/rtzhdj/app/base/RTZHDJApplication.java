package com.mytv.rtzhdj.app.base;

import android.content.Context;

import com.jess.arms.base.BaseApplication;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.utils.CrashHandler;
import com.mytv.rtzhdj.app.utils.sonic.SonicRuntimeImpl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

import org.xutils.x;

/**
 * 全局 Application
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update
 */
public class RTZHDJApplication extends BaseApplication {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //默认是 贝塞尔雷达Header
                return new BezierRadarHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //默认是 BallPulseFooter
                return new BallPulseFooter(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(this), new SonicConfig.Builder().build());
        }

        // 全局异常捕捉
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());

        // 初始化 xutils3
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能

    }

}
