package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsAllEntity;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsAllActivity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsSimpleActivity;

import java.util.List;

import io.reactivex.Observable;


public interface NewsAllContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(NewsAllEntity newsAllEntity);
        void loadListData(List<NewsDetailEntity> newsList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 带“全部”通用二级页面
        Observable<BaseJson<NewsAllEntity>> getTwoLevelAllList(int nodeId, int pageIndex, int pageSize, boolean update);

        // 获取 二级通用列表
        Observable<BaseJson<List<NewsDetailEntity>>> getTwoLevelInfoList(int nodeId, int pageIndex, int pageSize, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(NewsAllActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取带“全部”通用二级页面 api
        void callMethodOfGetTwoLevelAllList(int nodeId, int pageIndex, int pageSize, boolean update);

        // 调用 获取二级通用列表 api
        void callMethodOfGetTwoLevelInfoList(int nodeId, int pageIndex, int pageSize, boolean update);
    }
}
