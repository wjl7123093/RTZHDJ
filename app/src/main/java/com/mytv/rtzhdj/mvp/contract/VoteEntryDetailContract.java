package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.mvp.ui.activity.MyDonationActivity;
import com.mytv.rtzhdj.mvp.ui.activity.VoteEntryDetailActivity;

import java.util.List;

import io.reactivex.Observable;


public interface VoteEntryDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(VoteEntrylEntity voteEntrylEntity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 投票作品详情数据
        Observable<BaseJson<VoteEntrylEntity>> postOnlineVoteDetails(int id, boolean update);

        // post 投票
        Observable<BaseJson> postVoteSubmit(int contentId, int id, int userId);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(VoteEntryDetailActivity activity);

        // 调用 获取投票作品详情API
        void callMethodOfPostOnlineVoteDetails(int id, boolean update);

        // 调用 在线投票 api
        void callMethodOfPostVoteSubmit(int contentId, int id, int userId);
    }
}
