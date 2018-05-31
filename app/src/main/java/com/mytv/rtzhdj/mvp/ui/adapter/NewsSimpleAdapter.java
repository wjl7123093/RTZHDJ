package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * NewsSimpleAdapter 带"推荐"二级页面 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-8
 * @update
 */
public class NewsSimpleAdapter extends BaseQuickAdapter<NewsDetailEntity, BaseViewHolder> {

    private Context mContext;
    private boolean mIsHeader = false;

    public NewsSimpleAdapter(Context context, List<NewsDetailEntity> newsList, boolean isHeader) {
        super(R.layout.item_vlayout_list_image, newsList);
        mContext = context;
        mIsHeader = isHeader;
    }


    @Override
    protected void convert(BaseViewHolder helper, NewsDetailEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, item.getAddDate().split("T")[0]);
        helper.setText(R.id.tv_star_num, item.getDigs() + "");
        helper.setText(R.id.tv_comment_num, item.getComments() + "");

        if (!TextUtils.isEmpty(item.getAllImgUrl())) {
//            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
//                    Api.APP_IMAGE_DOMAIN + item.getImgUrl().replace("@", ""));
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
                    item.getAllImgUrl());
            helper.getView(R.id.iv_image).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_image).setVisibility(View.GONE);
        }

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);

        if (mIsHeader) {
            if (0 == position)
                return;
            else
                position = position - 1;
        }

        if (payloads.isEmpty()) {
            convert(holder, getItem(position));
        } else {
            getItem(position).setDigs(getItem(position).getDigs() + 1);
            // 局部刷新（只刷新列表项数据（点赞数），不刷新图片）
            holder.setText(R.id.tv_star_num, getItem(position).getDigs() + "");
        }

    }
}
