package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.ReportCommunityEntity;

import java.util.List;

/**
 * ReportCommunityAdapter 报到社区列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-27
 * @update
 */
public class ReportCommunityAdapter extends BaseQuickAdapter<ReportCommunityEntity, BaseViewHolder> {

    private Context mContext;

    public ReportCommunityAdapter(Context context, List<ReportCommunityEntity> communityList) {
        super(R.layout.item_rv_report_community, communityList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportCommunityEntity item) {
        helper.setText(R.id.tv_title, "报到社区: " + item.getPublishmentSystemName());
        helper.setText(R.id.tv_mobile_phone, "联系电话: " + item.getMobile());

    }


}
