package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.TeacherEntity;
import com.mytv.rtzhdj.mvp.ui.activity.TeacherListActivity;

import java.util.List;

import io.reactivex.Observable;


public interface TeacherListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadListData(List<TeacherEntity> teacherList, boolean update);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 二级通用列表
        Observable<BaseJson<List<TeacherEntity>>> getTeacherList(int currentSystemId, int nodeId, int pageIndex, int pageSize, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(TeacherListActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取二级通用列表 api
        void callMethodOfGetTeacherList(int currentSystemId, int nodeId, int pageIndex, int pageSize, boolean update);
    }
}
