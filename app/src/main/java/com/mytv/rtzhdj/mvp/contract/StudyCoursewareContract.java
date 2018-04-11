package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.StudyCoursewareEntity;
import com.mytv.rtzhdj.mvp.ui.activity.StudyCoursewareActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

import java.util.List;

import io.reactivex.Observable;


public interface StudyCoursewareContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setOnGridClick(int position, String title);
        void setOnListClick(int position);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 学习课件列表数据
        Observable<StudyCoursewareEntity> getCoursewareList(String typeId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(StudyCoursewareActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initList(List<CoursewareEntity> data);

        // 调用 获取学习课件列表数据
        void callMethodOfGetCoursewareList(String typeId, boolean update);
    }
}
