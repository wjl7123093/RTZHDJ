package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.entity.EffectEvaluationEntity;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;

import io.reactivex.Observable;


public interface EffectEvaluationContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 效果评测列表
        Observable<EffectEvaluationEntity> getTestList(int userId, int typeId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(Activity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取效果评测列表
        void callMethodOfGetTestList(int userId, int typeId, boolean update);
    }
}
