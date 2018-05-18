package com.mytv.rtzhdj.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.di.component.DaggerNewsDetailComponent;
import com.mytv.rtzhdj.di.module.NewsDetailModule;
import com.mytv.rtzhdj.mvp.contract.NewsDetailContract;
import com.mytv.rtzhdj.mvp.presenter.NewsDetailPresenter;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 新闻详情界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-3-2     填充UI布局
 *         2018-3-3     初始化 webview
 */
@Route(path = ARoutePath.PATH_NEWS_DETAIL)
public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.webProgressBar)
    WebProgressBar mWebProgressBar;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.tv_comment_num)
    TextView mTvCommentNum;
    @BindView(R.id.tv_star_num)
    TextView mTvStarNum;
    @BindView(R.id.iv_share)
    ImageView mIvShare;

    @Autowired
    int articleId;
    @Autowired
    int nodeId;
    @Autowired
    int digs;
    @Autowired
    int comments;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsDetailModule(new NewsDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_news_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(NewsDetailActivity.this);
        mPresenter.initWebview(mWebView, mWebProgressBar);
        mPresenter.getNewsDetail(Api.APP_ARTICLE_DOMAIN + "nodeId=" + nodeId + "&id=" + articleId);

        // 获取新闻详情
//        mPresenter.callMethodOfGetContent(articleId, nodeId, false);
        mTvStarNum.setText(digs + "");
        mTvCommentNum.setText(comments + "");

        mTvComment.setOnClickListener(view -> showDialog());
        mTvStarNum.setOnClickListener(view -> mPresenter.callMethodOfPostDoDig(nodeId, articleId, false));
        mTvCommentNum.setOnClickListener(view ->
            ARouter.getInstance().build(ARoutePath.PATH_COMMENT)
                    .withInt("nodeId", nodeId).withInt("contentId", articleId).navigation());
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
    public void loadWap(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void setWebviewProgress(int progress) {
        mWebProgressBar.setProgress(progress);
    }

    @Override
    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetailActivity.this);
        View dialogView = LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.dialog_comment, null);
        EditText mEdtComment = (EditText) dialogView.findViewById(R.id.edt_comment);
        builder.setView(dialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(mEdtComment.getText().toString().trim())) {
                    showMessage("评论内容不能为空");
                    return;
                }

                // 提交评论
                mPresenter.callMethodOfPostComment(DataHelper.getIntergerSF(NewsDetailActivity.this,
                        SharepreferenceKey.KEY_USER_ID), nodeId, articleId, mEdtComment.getText().toString().trim(), false);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void changeDigsStatus() {
        Drawable drawable= getResources().getDrawable(R.mipmap.ic_digs_select);

        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvStarNum.setCompoundDrawables(drawable,null,null,null);
        mTvStarNum.setText(digs + 1 + "");
    }
}
