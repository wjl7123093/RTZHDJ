package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceActivity;
import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceDetailActivity;

import io.reactivex.Observable;


public interface VolunteerServiceDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 志愿服务详情
        Observable<VolunteerDetailEntity> getVolunteerServiceDetail(String id, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(VolunteerServiceDetailActivity activity);

        // 调用 获取志愿服务详情数据
        void callMethodOfGetVolunteerServiceDetail(String id, boolean update);
    }
}
