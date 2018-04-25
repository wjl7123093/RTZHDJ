package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.QuestionaireEntitiy;
import com.mytv.rtzhdj.app.data.entity.VoteListEntity;

import java.util.List;

/**
 * VoteOnlineAdapter 在线投票列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-25
 * @update
 */
public class VoteOnlineAdapter extends BaseQuickAdapter<VoteListEntity, BaseViewHolder> {

    private Context mContext;

    public VoteOnlineAdapter(Context context, List<VoteListEntity> voteList) {
        super(R.layout.item_rv_online, voteList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteListEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, "活动时间: "
                + item.getAddDate().split("T")[0] + "至"
                + item.getEndDate().split("T")[0]);
        helper.setText(R.id.tv_status, item.getState() == 1 ? "进行中" : "已结束");
    }


}
