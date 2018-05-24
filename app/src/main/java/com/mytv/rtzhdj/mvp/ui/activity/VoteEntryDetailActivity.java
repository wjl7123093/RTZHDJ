package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;
import com.mytv.rtzhdj.di.component.DaggerVoteEntryDetailComponent;
import com.mytv.rtzhdj.di.module.VoteEntryDetailModule;
import com.mytv.rtzhdj.mvp.contract.VoteEntryDetailContract;
import com.mytv.rtzhdj.mvp.presenter.VoteEntryDetailPresenter;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 投票作品详情界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
@Route(path = ARoutePath.PATH_VOTE_ENTRY_DETAIL)
public class VoteEntryDetailActivity extends BaseActivity<VoteEntryDetailPresenter> implements VoteEntryDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.iv_image1)
    ImageView mIvImage1;
    @BindView(R.id.iv_image2)
    ImageView mIvImage2;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_rank)
    TextView mTvRank;
    @BindView(R.id.tv_votes)
    TextView mTvVotes;
    @BindView(R.id.btn_vote)
    Button mBtnVote;

    @Autowired
    int contentId;
    @Autowired
    int id;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVoteEntryDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .voteEntryDetailModule(new VoteEntryDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_vote_entry_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        // 获取 投票作品详情
        mPresenter.callMethodOfPostOnlineVoteDetails(id, false);

        // post 在线投票
        mBtnVote.setOnClickListener(view ->
            mPresenter.callMethodOfPostVoteSubmit(contentId, id,
                    DataHelper.getIntergerSF(VoteEntryDetailActivity.this, SharepreferenceKey.KEY_USER_ID)));
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
    public void loadData(VoteEntrylEntity voteEntrylEntity) {
        mTvTitle.setText(voteEntrylEntity.getTitle());
        mTvRank.setText("第" + voteEntrylEntity.getRanking() + "名");
        mTvVotes.setText(voteEntrylEntity.getVoteNum() + "票");
        ImageLoader.getInstance().showImage(VoteEntryDetailActivity.this, mIvImage1, voteEntrylEntity.getAllImgUrl());
        ImageLoader.getInstance().showImage(VoteEntryDetailActivity.this, mIvImage2, voteEntrylEntity.getAllImgUrl());
    }
}
