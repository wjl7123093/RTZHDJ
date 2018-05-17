package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.GradeRankEntity;

import java.util.List;

/**
 * GradeRankAdapter 成绩排行列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update
 */
public class GradeRankAdapter extends BaseQuickAdapter<GradeRankEntity.GradeRank, BaseViewHolder> {

    private Context mContext;

    public GradeRankAdapter(Context context, List<GradeRankEntity.GradeRank> gradeRankList) {
        super(R.layout.item_rv_grade_rank, gradeRankList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GradeRankEntity.GradeRank item) {
        helper.setText(R.id.tv_name, item.getUserName());
        helper.setText(R.id.tv_party_branch, item.getPublishmentSystemName());
//        helper.setText(R.id.tv_time, item.getTime());
        helper.setText(R.id.tv_grade, item.getScore() + "");
        helper.setText(R.id.tv_rank, helper.getPosition() + "");

//        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getImg_url());
    }


}
