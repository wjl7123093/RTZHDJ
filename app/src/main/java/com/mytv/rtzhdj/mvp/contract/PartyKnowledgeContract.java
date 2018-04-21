package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyKnowledgeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.mvp.ui.activity.PartyKnowledgeActivity;

import java.util.List;

import io.reactivex.Observable;


public interface PartyKnowledgeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void initAdapter(List<PartyNewsEntity> newsList);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 党建只是列表数据
        Observable<BaseJson<List<PartyNewsEntity>>> getPartyKnowledgeList(int nodeId, int pageIndex, int pageSize, boolean update);

    }
    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(PartyKnowledgeActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);
        List<PartyColumnsEntity> initColums();

        // 调用 获取党建只是列表数据
        void callMethodOfGetPartyKnowledgeList(int nodeId, int pageIndex, int pageSize, boolean update);
    }
}
