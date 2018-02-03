package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterAdapter extends BaseQuickAdapter<NewsEntity, BaseViewHolder> {

    private Context mContext;

    public HeaderAndFooterAdapter(Context context, int dataSize) {
        super(R.layout.item_vlayout_list_image, DataServer.getSampleData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, item.getDatetime());
        helper.setText(R.id.tv_star_num, item.getStar_num() + "");
        helper.setText(R.id.tv_comment_num, item.getComment_num() + "");

        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image), item.getImg_url());
    }


}
