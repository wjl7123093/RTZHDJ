package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.mvp.ui.activity.WishWallActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public interface WishWallContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<MyWishEntity> myWishList);
        void showDialog();
        void dismissDialog();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 我的心愿数据
        Observable<BaseJson<List<MyWishEntity>>> postMyWishList(int userId, int type, boolean update);

        // 获取 心愿墙数据
        Observable<BaseJson<List<MyWishEntity>>> getWishList(int userId, int type, boolean update);

        // 上传 我要许愿 数据
        Observable<BaseJson> postMyWish(Map<String, RequestBody> params,
                                        List<MultipartBody.Part> parts);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(WishWallActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取我的心愿API
        void callMethodOfPostMyWishList(int userId, int type, boolean update);

        // 调用 获取心愿墙API
        void callMethodOfGetWishList(int userId, int type, boolean update);

        // 调用 上传我要许愿数据
        void callMethodOfPostMyWish(Map<String, RequestBody> params,
                                    List<MultipartBody.Part> parts);
    }
}
