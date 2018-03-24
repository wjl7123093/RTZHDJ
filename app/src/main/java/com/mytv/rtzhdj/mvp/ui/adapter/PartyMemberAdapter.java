package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * PartyMemberAdapter 党员风采列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-11
 * @update
 */
public class PartyMemberAdapter extends BaseQuickAdapter<PartyMemberEntity, BaseViewHolder> {

    private Context mContext;

    public PartyMemberAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_party_member, DataServer.getPartyMemberData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PartyMemberEntity item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_phone, item.getMobile());

        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getUrl());

        helper.getView(R.id.tv_msg).setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_SEND_MSG)
                    .withString("id", "").withString("name", "").navigation();
        });
    }


}
