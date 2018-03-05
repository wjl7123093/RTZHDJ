package com.mytv.rtzhdj.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * CommentBar 底部评论条
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-3
 * @update
 */
public class CommentBar extends LinearLayout {

    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.tv_comment_num)
    TextView mTvCommentNum;
    @BindView(R.id.tv_star_num)
    TextView mTvStarNum;
    @BindView(R.id.iv_share)
    ImageView mIvShare;

    private Context mContext;

    public CommentBar(Context context) {
        super(context, null);
        mContext = context;
        initView();
    }

    public CommentBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public CommentBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.include_comment_bar, this);
        ButterKnife.bind(this);
    }

    /**
     * 评论
     * @param v
     */
    @OnClick(R.id.tv_comment)
    public void onCommentClick(View v) {
        // 弹出对话框，输入评论内容


    }

    /**
     * 路由 评论列表
     * @param v
     */
    @OnClick(R.id.tv_comment_num)
    public void onCommentNumClick(View v) {
        ARouter.getInstance().build(ARoutePath.PATH_COMMENT).navigation();
    }

    /**
     * 点赞
     * @param v
     */
    @OnClick(R.id.tv_star_num)
    public void onStarNumClick(View v) {
        // 根据点赞状态改变图标颜色


    }

    /**
     * 分享
     * @param v
     */
    @OnClick(R.id.iv_share)
    public void onShareClick(View v) {
        // 调用友盟分享


    }
}
