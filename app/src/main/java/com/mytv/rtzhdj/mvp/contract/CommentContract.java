package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.mvp.ui.activity.CommentActivity;

import java.util.List;

import io.reactivex.Observable;


public interface CommentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<CommentEntity> commentList, boolean update);
        void showDialog();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取评论列表
        Observable<BaseJson<List<CommentEntity>>> getCommentList(int currentSystemId, int nodeId, int contentId, boolean update);

        // post 评论数据
        Observable<BaseJson> postComment(int userId, int nodeId, int contentId, String commentInfo, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(CommentActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取评论列表
        void callMethodOfGetCommentList(int currentSystemId, int nodeId, int contentId, boolean refresh);

        // 调用 评论
        void callMethodOfPostComment(int userId, int nodeId, int contentId, String commentInfo, boolean update);
    }
}
