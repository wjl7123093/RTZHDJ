package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * VoteResultAdapter 投票作品列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
public class VoteResultAdapter extends BaseQuickAdapter<VoteEntrylEntity, BaseViewHolder> {

    private Context mContext;

    public VoteResultAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_vote_result, DataServer.getVoteEntryData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteEntrylEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_rank, "第" + item.getRanking() + "名");
        helper.setText(R.id.tv_votes, item.getVoteNum() + "");

    }


}
