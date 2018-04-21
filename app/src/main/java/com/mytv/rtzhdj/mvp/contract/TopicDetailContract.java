package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.SpecialColumnsEntity;
import com.mytv.rtzhdj.mvp.ui.activity.TopicDetailActivity;

import java.util.List;

import io.reactivex.Observable;


public interface TopicDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void initBackground(SpecialColumnsEntity specialColumnsEntity);
        void initTab(SpecialColumnsEntity specialColumnsEntity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取专题二级栏目
        Observable<BaseJson<SpecialColumnsEntity>> getSpecialClass(int nodeId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(TopicDetailActivity activity);

        // 调用 获取专题二级栏目
        void callMethodOfGetSpecialClass(int nodeId, boolean update);

    }
}
