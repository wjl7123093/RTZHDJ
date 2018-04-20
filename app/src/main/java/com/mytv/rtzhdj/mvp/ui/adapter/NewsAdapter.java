package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class NewsAdapter extends BaseQuickAdapter<PartyRecommendEntity.ImportandBlock, BaseViewHolder> {

    private Context mContext;

    public NewsAdapter(Context context, List<PartyRecommendEntity.ImportandBlock> newsList) {
        super(R.layout.item_vlayout_list_image, newsList);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, PartyRecommendEntity.ImportandBlock item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, item.getAddDate());
        helper.setText(R.id.tv_star_num, item.getDigs() + "");
        helper.setText(R.id.tv_comment_num, item.getComments() + "");

        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
                Api.APP_IMAGE_DOMAIN + item.getImageUrl().replace("@", ""));

    }
}
