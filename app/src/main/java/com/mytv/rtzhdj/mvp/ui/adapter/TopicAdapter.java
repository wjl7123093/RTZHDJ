package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * TopicAdapter 专题列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-6
 * @update
 */
public class TopicAdapter extends BaseQuickAdapter<NewsEntity, BaseViewHolder> {

    private Context mContext;

    public TopicAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_topic, DataServer.getSampleData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {
        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image), item.getImg_url());
    }


}
