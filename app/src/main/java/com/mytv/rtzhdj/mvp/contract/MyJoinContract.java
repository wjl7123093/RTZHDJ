package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.mvp.ui.activity.DonateActivity;
import com.mytv.rtzhdj.mvp.ui.activity.MyJoinActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public interface MyJoinContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 上传 参与志愿服务 数据
        Observable<BaseJson> postJoinVolunteerService(Map<String, RequestBody> params,
                                                      List<MultipartBody.Part> parts);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(MyJoinActivity activity);

        // 调用 上传参与志愿服务数据
        void callMethodOfPostJoinVolunteerService(Map<String, RequestBody> params,
                                                  List<MultipartBody.Part> parts);

    }
}
