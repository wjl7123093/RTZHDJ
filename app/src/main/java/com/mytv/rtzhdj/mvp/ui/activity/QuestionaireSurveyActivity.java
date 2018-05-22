package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.AnswerEntity;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.di.component.DaggerQuestionaireSurveyComponent;
import com.mytv.rtzhdj.di.module.QuestionaireSurveyModule;
import com.mytv.rtzhdj.mvp.contract.QuestionaireSurveyContract;
import com.mytv.rtzhdj.mvp.presenter.QuestionaireSurveyPresenter;
import com.mytv.rtzhdj.mvp.ui.widget.CenterDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 在线问卷调查 页面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-22
 * @update
 */
@Route(path = ARoutePath.PATH_QUESTIONAIRE_SURVEY)
public class QuestionaireSurveyActivity extends BaseActivity<QuestionaireSurveyPresenter> implements QuestionaireSurveyContract.View {

    public static final int RESULT_CODE_QUESTIONNAIRE = 0x402;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.llQuestion)
    LinearLayout mLlQuestion;
    @BindView(R.id.btnSubmit)
    Button mBtnSubmit;

    @Autowired
    int onlineSurveyId;

    /** 加载进度条 */
    private CenterDialog centerDialog;

    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater xInflater;

    private List<QuestionEntity> mQuestionList;
    private List<AnswerEntity> mAnswerList;
    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist=new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;

    private List<List<TextView>> txtListParent = new ArrayList<List<TextView>>();
    private List<TextView> txtList;

    private int mScore = 0; // 评测结果分数
    private boolean mIsAnswerCorret = false;    // 是否正确答案
    private String mAnswerString = "";  // 所选答案


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerQuestionaireSurveyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .questionaireSurveyModule(new QuestionaireSurveyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_questionaire_survey; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);
        centerDialog = new CenterDialog(QuestionaireSurveyActivity.this, R.layout.dialog_comment,
                new int[]{});

        xInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 获取 问卷调查信息
        mPresenter.callMethodOfGetSurveyDetailList(onlineSurveyId, false);

        mBtnSubmit.setOnClickListener(view -> {
            // 提交 评测结果
            doSubmit();
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void loadData(List<QuestionEntity> questionList) {
        mQuestionList = questionList;
        bindDataToUI(mQuestionList);
        displayQuestionnaireResult(mQuestionList);
    }


    /**
     * 绑定数据到UI
     * （渲染问卷调查页面）
     * @param questionList
     */
    private void bindDataToUI(List<QuestionEntity> questionList) {
        //根据第二层问题的多少，来动态加载布局
        for(int i=0;i<questionList.size();i++){
            que_view=xInflater.inflate(R.layout.layout_question, null);
            TextView txt_que=(TextView)que_view.findViewById(R.id.txt_question_item);
            //这是第三层布局要加入的地方
            LinearLayout add_layout=(LinearLayout)que_view.findViewById(R.id.lly_answer);
            //判断单选-多选来实现后面是*号还是*多选，
            if(questionList.get(i).getIsMulti() == 1){ // 多选
                set(txt_que,(i+1) + ". " + questionList.get(i).getTitle(),1);
            }else{
                set(txt_que,(i+1) + ". " + questionList.get(i).getTitle(),0);
            }
            //获得答案即第三层数据
            mAnswerList = questionList.get(i).getItems();
            imglist2=new ArrayList<ImageView>();
            txtList = new ArrayList<TextView>();
            for(int j=0;j<mAnswerList.size();j++){
                ans_view=xInflater.inflate(R.layout.layout_answer, null);
                TextView txt_ans=(TextView)ans_view.findViewById(R.id.txt_answer_item);
                ImageView image=(ImageView)ans_view.findViewById(R.id.image);
                View line_view=ans_view.findViewById(R.id.vw_line);
                if(j==mAnswerList.size()-1){
                    //最后一条答案下面不要线是指布局的问题
                    line_view.setVisibility(View.GONE);
                }
                //判断单选多选加载不同选项图片
                if(questionList.get(i).getIsMulti() == 1){
                    image.setBackgroundDrawable(getResources().getDrawable(R.mipmap.multiselect_false));
                }else{
                    image.setBackgroundDrawable(getResources().getDrawable(R.mipmap.radio_false));
                }
                Log.e("---", "------"+image);
                imglist2.add(image);
                txtList.add(txt_ans);
                txt_ans.setText(mAnswerList.get(j).getContent());
                LinearLayout lly_answer_size=(LinearLayout)ans_view.findViewById(R.id.lly_answer_size);
                lly_answer_size.setOnClickListener(new QuestionaireSurveyActivity.answerItemOnClickListener(i, j, mAnswerList, txt_ans, txtList));
                add_layout.addView(ans_view);
            }
			/*for(int r=0; r<imglist2.size();r++){
				Log.e("---", "imglist2--------"+imglist2.get(r));
			}*/

            imglist.add(imglist2);
            txtListParent.add(txtList);

            mLlQuestion.addView(que_view);
        }
		/*for(int q=0;q<imglist.size();q++){
			for(int w=0;w<imglist.get(q).size();w++){
				Log.e("---", "共有------"+imglist.get(q).get(w));
			}
		}*/

    }

    private void set(TextView tv_test, String content,int type) {
        //为了加载问题后面的* 和*多选
        // TODO Auto-generated method stub
        String w;
        if(type==1){
            w = content+"*[多选]";
        }else{
            w = content+"*";
        }

        int start = content.length();
        int end = w.length();
        Spannable word = new SpannableString(w);
        word.setSpan(new AbsoluteSizeSpan(40), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_test.setText(word);
    }

    /**
     * 提交问卷调查
     */
    private void doSubmit() {
        //判断是否答完题
        boolean isState=true;
        //最终要的json数组
        JSONArray jsonArray = new JSONArray();
        String strResult = "";  // 问卷结果
        //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
        //注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
        for(int i=0;i<mQuestionList.size();i++){
            mAnswerList=mQuestionList.get(i).getItems();
            //判断是否有题没答完
            if(mQuestionList.get(i).getQue_state()==0){
                Toast.makeText(getApplicationContext(), "您第"+(i+1)+"调查没有答完", Toast.LENGTH_LONG).show();
                jsonArray=null;
                isState=false;
                break;
            }else{

                if (mQuestionList.get(i).getIsMulti() == 1) {  //　多选
                    for(int j=0;j<mAnswerList.size();j++){
                        if(mAnswerList.get(j).getAns_state()==1){
                            strResult += "<p><span class='text-color-red'>问：</span>" + mQuestionList.get(i).getTitle() + "</p>";
                            strResult += "<p><span class='text-color-blue'>答：</span>"
                                    + mAnswerList.get(j) + "</p>";

                            // 拼接答案
                            mAnswerString += mAnswerList.get(j).getOptionID() + ",";
                        }
                    }

                } else {    // 单选
                    strResult += "<p><span class='text-color-red'>问：</span>" + mQuestionList.get(i).getTitle() + "</p>";
                    for(int j=0;j<mAnswerList.size();j++){
                        if(mAnswerList.get(j).getAns_state()==1){
                            strResult += "<p><span class='text-color-blue'>答：</span>"
                                    + mAnswerList.get(j) + "</p>";

                            // 拼接答案
                            mAnswerString += mAnswerList.get(j).getOptionID() + ",";
                            break;
                        }
                    }
                }

            }

        }
        if(isState){    // 答完题了
//            mApplication.setmQuestionList(mQuestionList);
//            mApplication.setmIsDidQuestionnaire(true);

//            Intent data = new Intent();
//            data.putExtra("result", strResult);
//            this.setResult(RESULT_CODE_QUESTIONNAIRE, data);
//            this.finish();

            // 提交 问卷调查结果
            mPresenter.callMethodOfPostSurveyInfo(DataHelper.getIntergerSF(QuestionaireSurveyActivity.this,
                    SharepreferenceKey.KEY_USER_ID), mAnswerString, false);
//            showMessage("答题结束，分数为：" + mScore);
        }
    }

    class answerItemOnClickListener implements View.OnClickListener {
        private int i;
        private int j;
        private TextView txt;
        private List<AnswerEntity> the_answer_lists;
        private List<TextView> txtList;
        public answerItemOnClickListener(int i,int j, List<AnswerEntity> the_answer_list,TextView text,
                                         List<TextView> txtList){
            this.i=i;
            this.j=j;
            this.the_answer_lists=the_answer_list;
            this.txt=text;
            this.txtList = txtList;

        }
        //实现点击选项后改变选中状态以及对应图片
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断当前问题是单选还是多选
			/*Log.e("------", "选择了-----第"+i+"题");
			for(int q=0;q<imglist.size();q++){
				for(int w=0;w<imglist.get(q).size();w++){
//					Log.e("---", "共有------"+imglist.get(q).get(w));
				}
			}
			Log.e("----", "点击了---"+imglist.get(i).get(j));*/

            if(mQuestionList.get(i).getIsMulti() == 1){
                //多选
                if(the_answer_lists.get(j).getAns_state()==0){
                    //如果未被选中
                    txt.setTextColor(Color.parseColor("#EA5514"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.mipmap.multiselect_true));
                    the_answer_lists.get(j).setAns_state(1);
                    mQuestionList.get(i).setQue_state(1);
                }else{
                    txt.setTextColor(Color.parseColor("#595757"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.mipmap.multiselect_false));
                    the_answer_lists.get(j).setAns_state(0);
                    mQuestionList.get(i).setQue_state(1);
                }
            }else{
                //单选

                for(int z=0;z<the_answer_lists.size();z++){
                    txtList.get(z).setTextColor(Color.parseColor("#595757"));
                    the_answer_lists.get(z).setAns_state(0);
                    imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.mipmap.radio_false));
                }
                if(the_answer_lists.get(j).getAns_state()==0){
                    //如果当前未被选中
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.mipmap.radio_true));
                    the_answer_lists.get(j).setAns_state(1);
                    mQuestionList.get(i).setQue_state(1);
                }else{
                    //如果当前已被选中
                    the_answer_lists.get(j).setAns_state(1);
                    mQuestionList.get(i).setQue_state(1);
                }
                txt.setTextColor(Color.parseColor("#EA5514"));

            }
            //判断当前选项是否选中



        }

    }

    /**
     * 渲染问卷结果
     * @param questionBeanList
     */
    private void displayQuestionnaireResult(List<QuestionEntity> questionBeanList) {
        for (int i = 0; i < questionBeanList.size(); i++) {
            List<AnswerEntity> the_answer_lists = questionBeanList.get(i).getItems();
            if (questionBeanList.get(i).getIsMulti() == 1) {   // 多选
                for (int j = 0; j < the_answer_lists.size(); j++) {
                    if(the_answer_lists.get(j).getAns_state()==0){
                        //如果未被选中
//                        txtList.get(j).setTextColor(Color.parseColor("#EA5514"));
                        txtListParent.get(i).get(j).setTextColor(Color.parseColor("#595757"));
                        imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.mipmap.multiselect_false));
//                        the_answer_lists.get(j).setAns_state(1);
//                        questionBeanList.get(i).setQue_state(1);
                    }else{
//                        txtList.get(j).setTextColor(Color.parseColor("#595757"));
                        txtListParent.get(i).get(j).setTextColor(Color.parseColor("#EA5514"));
                        imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.mipmap.multiselect_true));
//                        the_answer_lists.get(j).setAns_state(0);
//                        questionBeanList.get(i).setQue_state(1);
                    }
                }

            } else { // 单选
                for (int j = 0; j < the_answer_lists.size(); j++) {
//                    for(int z=0;z<the_answer_lists.size();z++){
//                        txtList.get(z).setTextColor(Color.parseColor("#595757"));
//                        imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
//                    }
                    if(the_answer_lists.get(j).getAns_state()==1){
                        //如果当前被选中
                        imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.mipmap.radio_true));
                        txtListParent.get(i).get(j).setTextColor(Color.parseColor("#EA5514"));
//                        the_answer_lists.get(j).setAns_state(1);
//                        questionBeanList.get(i).setQue_state(1);
                    }else{
                        //如果当前已被选中
//                        the_answer_lists.get(j).setAns_state(1);
//                        questionBeanList.get(i).setQue_state(1);
                    }
                }
            }


        }
    }

}
