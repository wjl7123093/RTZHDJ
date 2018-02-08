package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CommunityEntity;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * SurroundingCommunityAdapter 周边社区列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-8
 * @update
 */
public class SurroundingCommunityAdapter extends BaseQuickAdapter<CommunityEntity, BaseViewHolder> {

    private Context mContext;

    public SurroundingCommunityAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_surrounding_community, DataServer.getCommunityData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommunityEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image), item.getUrl());
    }


}
