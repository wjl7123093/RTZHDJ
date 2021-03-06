package com.mytv.rtzhdj.mvp.contract;

import android.webkit.WebView;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;


public interface WebviewContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setWebviewProgress(int progress);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }


    interface Presenter extends IPresenter {
        void initWebview(WebView mWebView, WebProgressBar mWebProgressBar);
    }
}
