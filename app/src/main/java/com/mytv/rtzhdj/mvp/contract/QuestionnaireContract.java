package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.app.data.entity.ScoresDetailsEntity;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionnaireActivity;

import java.util.List;

import io.reactivex.Observable;


public interface QuestionnaireContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<QuestionEntity> questionList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 效果评测问卷
        Observable<BaseJson<List<QuestionEntity>>> getTestInfo(int examinationId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(QuestionnaireActivity activity);

        // 调用 获取效果评测问卷
        void callMethodOfGetTestInfo(int examinationId, boolean refresh);
    }
}