package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.MembershipEntity;

import java.util.List;

/**
 * MembershipAdapter 组织关系列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-24
 * @update
 */
public class MembershipAdapter extends BaseQuickAdapter<MembershipEntity, BaseViewHolder> {

    private Context mContext;

    public MembershipAdapter(Context context, List<MembershipEntity> membershipList) {
        super(R.layout.item_rv_relationship, membershipList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MembershipEntity item) {
        helper.setText(R.id.tv_party_branch1, item.getTransFromOrganization());
        helper.setText(R.id.tv_party_branch2, item.getTransInOrganization());
        helper.setText(R.id.tv_time, item.getOperateDate().replace("T", " "));

    }


}
