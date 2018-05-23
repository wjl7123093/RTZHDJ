package com.mytv.rtzhdj.mvp.contract;

import android.webkit.WebView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.mvp.ui.activity.CourseDetailActivity;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import io.reactivex.Observable;


public interface CourseDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadWap(String url);
        void setWebviewProgress(int progress);

        void showData(CoursewareDetailEntity coursewareDetailEntity);
        void showDialog();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 课件详情
        Observable<BaseJson<CoursewareDetailEntity>> getCoursewareDetail(int id, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(CourseDetailActivity activity);
        void getCourseDetail(String url);
        void initWebview(WebView mWebView, WebProgressBar mWebProgressBar);

        // 调用 获取课件详情
        void callMethodOfGetCoursewareDetail(int id, boolean update);
    }
}
