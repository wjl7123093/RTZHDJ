package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;

import java.util.List;

import io.reactivex.Observable;


public interface CompulsoryCourseContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<CoursewareEntity> courseList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 党建新闻二级列表数据
        Observable<BaseJson<List<CoursewareEntity>>> getCoursewareList(int userId,
                                                                       int nodeId,
                                                                       int studyState,
                                                                       int pageIndex,
                                                                       int pageSize,
                                                                       boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(Activity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取学习课件（必修课）数据
        void callMethodOfGetCoursewareList(int userId, int nodeId, int studyState,
                                           int pageIndex, int pageSize, boolean update);
    }
}
