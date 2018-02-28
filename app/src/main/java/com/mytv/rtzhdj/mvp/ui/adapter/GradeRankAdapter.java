package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.GradeRankEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * GradeRankAdapter 成绩排行列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update
 */
public class GradeRankAdapter extends BaseQuickAdapter<GradeRankEntity, BaseViewHolder> {

    private Context mContext;

    public GradeRankAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_grade_rank, DataServer.getGradeRankData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GradeRankEntity item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_party_branch, item.getParty_branch());
        helper.setText(R.id.tv_time, item.getTime());
        helper.setText(R.id.tv_grade, item.getGrade() + "");
        helper.setText(R.id.tv_rank, item.getRank() + "");

        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getImg_url());
    }


}
