package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * PartyMemberAdapter 党员风采列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-11
 * @update 2018-4-26    对接 getPartyMember 接口数据
 *         2018-5-9     对接 getPartymembermien 接口数据
 */
public class PartyMemberAdapter extends BaseQuickAdapter<PartyMienEntity, BaseViewHolder> {

    private Context mContext;

    public PartyMemberAdapter(Context context, List<PartyMienEntity> memberList) {
        super(R.layout.item_rv_party_member, memberList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PartyMienEntity item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_phone, item.getTelePhone());

        if (!TextUtils.isEmpty(item.getImageUrl()))
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getImageUrl());

        helper.getView(R.id.tv_msg).setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_SEND_MSG)
                    .withInt("id", item.getID())
                    .withString("name", item.getTitle()).navigation();
        });
    }


}
