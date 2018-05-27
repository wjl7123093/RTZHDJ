package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.mvp.ui.activity.PartyMemberDetailActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


public interface PartyMemberDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(PartyMienEntity memberInfo);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 党员详情
        Observable<BaseJson<PartyMienEntity>> getPartyMemberDetail(int memberId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(PartyMemberDetailActivity activity);
        // 调用 获取党员详情接口
        void callMethodOfGetPartyMemberDetails(@NonNull int memberId, boolean udpate);
    }
}
