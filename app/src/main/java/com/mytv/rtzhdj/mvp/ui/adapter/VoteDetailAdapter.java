package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * VoteDetailAdapter 投票作品列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
public class VoteDetailAdapter extends BaseQuickAdapter<VoteDetailEntity, BaseViewHolder> {

    private Context mContext;

    public VoteDetailAdapter(Context context, List<VoteDetailEntity> voteDetailList) {
        super(R.layout.item_rv_vote_detail, voteDetailList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteDetailEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_rank, "票数: " + item.getPoll());

        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
                Api.APP_IMAGE_DOMAIN + item.getImgUrl().replace("@", ""));
    }


}
