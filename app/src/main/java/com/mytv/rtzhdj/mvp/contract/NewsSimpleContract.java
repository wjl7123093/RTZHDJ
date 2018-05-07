package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsSimpleActivity;

import java.util.List;

import io.reactivex.Observable;


public interface NewsSimpleContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(NewsSimpleEntity newsSimpleEntity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 带“推荐”通用二级页面
        Observable<BaseJson<NewsSimpleEntity>> getTwoLevelList(int nodeId, int pageIndex, int pageSize, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(NewsSimpleActivity activity);

        // 调用 获取带“推荐”通用二级页面 api
        void callMethodOfGetTwoLevelList(int nodeId, int pageIndex, int pageSize, boolean update);
    }
}
