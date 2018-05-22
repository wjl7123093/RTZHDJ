package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionaireSurveyActivity;

import java.util.List;

import io.reactivex.Observable;


public interface QuestionaireSurveyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<QuestionEntity> questionList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 在线问卷问题列表
        Observable<BaseJson<List<QuestionEntity>>> getSurveyDetailList(int examinationId, boolean update);

        // 提交 在线问卷结果
        Observable<BaseJson> postSurveyInfo(int userID, String answerString, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(QuestionaireSurveyActivity activity);

        // 调用 获取在线问卷问题列表
        void callMethodOfGetSurveyDetailList(int examinationId, boolean refresh);

        // 调用 提交在线问卷结果
        void callMethodOfPostSurveyInfo(int userID, String answerString, boolean refresh);

    }
}
