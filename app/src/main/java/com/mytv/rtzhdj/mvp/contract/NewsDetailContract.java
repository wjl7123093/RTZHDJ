package com.mytv.rtzhdj.mvp.contract;

import android.webkit.WebView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsDetailActivity;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import io.reactivex.Observable;


public interface NewsDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadWap(String url);
        void setWebviewProgress(int progress);
        void showDialog();

        void changeDigsStatus(int type);    // 更新点赞状态
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 新闻详情
        Observable<BaseJson<NewsDetailEntity>> getContent(int id, int nodeId, boolean update);

        // post 评论数据
        Observable<BaseJson> postComment(int userId, int nodeId, int contentId, String commentInfo, boolean update);

        // post 点赞
        Observable<BaseJson> postDoDig(int nodeId, int contentId, int type, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(NewsDetailActivity activity);
        void getNewsDetail(String url);
        void initWebview(WebView mWebView, WebProgressBar mWebProgressBar);

        // 调用 获取新闻详情
        void callMethodOfGetContent(int id, int nodeId, boolean update);
        // 调用 评论
        void callMethodOfPostComment(int userId, int nodeId, int contentId, String commentInfo, boolean update);
        // 调用 点赞
        void callMethodOfPostDoDig(int nodeId, int contentId, int type, boolean update);

    }
}
