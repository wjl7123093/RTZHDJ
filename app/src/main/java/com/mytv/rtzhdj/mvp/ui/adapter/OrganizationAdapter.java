package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.OrganizationEntity;

import java.util.List;
/**
 * OrganizationAdapter 组织活动列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-22
 * @update
 */
public class OrganizationAdapter extends BaseQuickAdapter<OrganizationEntity, BaseViewHolder> {

    private Context mContext;

    public OrganizationAdapter(Context context, List<OrganizationEntity> organizationList) {
        super(R.layout.item_rv_organization, organizationList);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, OrganizationEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_address, "地点: " + item.getAddress());
        helper.setText(R.id.tv_star_num, item.getDigs() + "");
        helper.setText(R.id.tv_comment_num, item.getComments() + "");

    }
}
