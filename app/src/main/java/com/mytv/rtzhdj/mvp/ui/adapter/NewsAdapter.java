package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class NewsAdapter extends BaseQuickAdapter<PartyNewsEntity, BaseViewHolder> {

    private Context mContext;

    public NewsAdapter(Context context, List<PartyNewsEntity> newsList) {
        super(R.layout.item_vlayout_list_image, newsList);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, PartyNewsEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, item.getAddDate());
        helper.setText(R.id.tv_star_num, item.getDigs() + "");
        helper.setText(R.id.tv_comment_num, item.getComments() + "");

        if (!TextUtils.isEmpty(item.getAllImgUrl())) {
//            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
//                    Api.APP_IMAGE_DOMAIN + item.getImageUrl().replace("@", ""));
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
                    item.getAllImgUrl());
            helper.getView(R.id.iv_image).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_image).setVisibility(View.GONE);
        }

    }
}
