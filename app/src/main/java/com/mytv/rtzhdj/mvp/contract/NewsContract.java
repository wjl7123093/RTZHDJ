package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;

import java.util.List;

import io.reactivex.Observable;


public interface NewsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void initTab(List<PartyColumnsEntity> partyColumnsList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        // 获取党建新闻二级栏目信息
        Observable<BaseJson<List<PartyColumnsEntity>>> getPartyColumns(int typedId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(Activity activity);

        // 调用 获取党建新闻二级栏目数据
        void callMethodOfGetPartyColumns(int typeId, boolean update);
    }
}
