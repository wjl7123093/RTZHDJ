package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsEducationActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

import java.util.List;

import io.reactivex.Observable;


public interface NewsEducationContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setOnGridClick(int position, String title);
        void setOnListClick(NewsDetailEntity newsDetailEntity, int position);

        void loadData(NewsSimpleEntity newsSimpleEntity, boolean update);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 带“推荐”通用二级页面
        Observable<BaseJson<NewsSimpleEntity>> getTwoLevelList(int nodeId, int pageIndex, int pageSize, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(NewsEducationActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initList(List<NewsDetailEntity> data);

        // 调用 获取带“推荐”通用二级页面 api
        void callMethodOfGetTwoLevelList(int nodeId, int pageIndex, int pageSize, boolean update);
    }
}
