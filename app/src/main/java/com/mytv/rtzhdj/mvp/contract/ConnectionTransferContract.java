package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.mvp.ui.activity.ConnectionTransferActivity;

import io.reactivex.Observable;


public interface ConnectionTransferContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 组织机构变更结果
        Observable<String> getOrganizationalChange(int publishmentsystemId, String reason, int userId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(ConnectionTransferActivity activity);

        // 调用 获取组织机构变更结果
        void callMethodOfGetOrganizationalChange(int publishmentsystemId, String reason, int userId, boolean update);
    }
}
