package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.GradeRankEntity;
import com.mytv.rtzhdj.mvp.ui.activity.GradeRankActivity;

import io.reactivex.Observable;


public interface GradeRankContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(GradeRankEntity gradeRankEntity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取成绩排行列表
        Observable<BaseJson<GradeRankEntity>> getTestResultList(int examinationID, int userID, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(GradeRankActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取成绩排行列表
        void callMethodOfGetTestResultList(int examinationID, int userID, boolean refresh);
    }
}
