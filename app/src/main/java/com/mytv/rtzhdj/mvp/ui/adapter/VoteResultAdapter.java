package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * VoteResultAdapter 投票作品列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update 2018-5-3     update 对接接口数据
 */
public class VoteResultAdapter extends BaseQuickAdapter<VoteDetailEntity, BaseViewHolder> {

    private Context mContext;

    public VoteResultAdapter(Context context, List<VoteDetailEntity> voteResultList) {
        super(R.layout.item_rv_vote_result, voteResultList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteDetailEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_rank, "第" + item.getRanking() + "名");
        helper.setText(R.id.tv_votes, item.getVoteNum() + "");

    }


}
