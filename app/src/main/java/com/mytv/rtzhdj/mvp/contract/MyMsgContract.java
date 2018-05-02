package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyMsgEntity;
import com.mytv.rtzhdj.mvp.ui.activity.MyMsgActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


public interface MyMsgContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<MyMsgEntity> msgList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 发送私信
        Observable<BaseJson<List<MyMsgEntity>>> postMyMessage(int userId, int messageType);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(MyMsgActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 发送私信接口
        void callMethodOfPostMyMessage(@NonNull int userId,
                                       @NonNull int messageType);
    }
}
