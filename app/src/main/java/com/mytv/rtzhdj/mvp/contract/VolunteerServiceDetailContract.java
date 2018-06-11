package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceDetailActivity;

import io.reactivex.Observable;


public interface VolunteerServiceDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(VolunteerDetailEntity volunteerDetailEntity);

        void changeDigsStatus(int type);    // 更新点赞状态
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 志愿服务详情
        Observable<BaseJson<VolunteerDetailEntity>> getVolunteerServiceDetail(int id, int userId, boolean update);

        // post 点赞
        Observable<BaseJson> postDoDig(int currentSystemId, int nodeId, int contentId, int type, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(VolunteerServiceDetailActivity activity);

        // 调用 获取志愿服务详情数据
        void callMethodOfGetVolunteerServiceDetail(int id, int userId, boolean update);
        // 调用 点赞
        void callMethodOfPostDoDig(int currentSystemId, int nodeId, int contentId, int type, boolean update);
    }
}
