package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.ScoresDetailsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * ScoresDetailsAdapter 积分明细页面 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-10
 * @update
 */
public class ScoresDetailsAdapter extends BaseQuickAdapter<ScoresDetailsEntity.DetailScore, BaseViewHolder> {

    private Context mContext;

    public ScoresDetailsAdapter(Context context, List<ScoresDetailsEntity.DetailScore> detailScoreList) {
        super(R.layout.item_rv_scores_details, detailScoreList);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, ScoresDetailsEntity.DetailScore item) {
        helper.setText(R.id.tv_scores, "积分数量:" + item.getIntegralValue() + "  操作类型: " + item.getGetType());
        helper.setText(R.id.tv_time, "时间: " + item.getGetTime());

    }
}
