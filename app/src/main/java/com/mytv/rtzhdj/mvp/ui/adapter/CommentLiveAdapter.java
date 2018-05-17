package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.PartyLiveEntity;

import java.util.List;

/**
 * CommentLiveAdapter 党建直播评论列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-17
 * @update
 */
public class CommentLiveAdapter extends BaseQuickAdapter<PartyLiveEntity.Comment, BaseViewHolder> {

    private Context mContext;

    public CommentLiveAdapter(Context context, List<PartyLiveEntity.Comment> commentList) {
        super(R.layout.item_rv_comment, commentList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PartyLiveEntity.Comment item) {
        helper.setText(R.id.tv_name, item.getUserName());
        helper.setText(R.id.tv_time, item.getAddDate());
//        helper.setText(R.id.tv_star_num, item.getLikeNum() + "");
        helper.setText(R.id.tv_content, item.getContent() + "");

//        if (!TextUtils.isEmpty(item.getAvatarUrl()))
//            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getAvatarUrl());
    }


}
