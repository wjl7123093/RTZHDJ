package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * CommentAdapter 评论列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update 2018-5-3     update 接口数据适配
 */
public class CommentAdapter extends BaseQuickAdapter<CommentEntity, BaseViewHolder> {

    private Context mContext;

    public CommentAdapter(Context context, List<CommentEntity> commentList) {
        super(R.layout.item_rv_comment, commentList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        helper.setText(R.id.tv_name, item.getUserName());
        helper.setText(R.id.tv_time, item.getCommentTime());
        helper.setText(R.id.tv_star_num, item.getLikeNum() + "");
        helper.setText(R.id.tv_content, item.getCommentInfo() + "");

        if (!TextUtils.isEmpty(item.getAvatarUrl()))
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getAvatarUrl());
    }


}
