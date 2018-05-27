package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * MyWishAdapter 心愿列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-27
 * @update
 */
public class MyWishAdapter extends BaseQuickAdapter<MyWishEntity, BaseViewHolder> {

    private Context mContext;

    public MyWishAdapter(Context context, List<MyWishEntity> myWishList) {
        super(R.layout.item_rv_mywish, myWishList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyWishEntity item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_status, item.isAuditStatus() ? "审核完成" : "待审核");

        if (TextUtils.isEmpty(item.getAllImgUrl())) {
            helper.setGone(R.id.iv_image, false);
        } else {
            helper.setGone(R.id.iv_image, true);
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image), item.getAllImgUrl());
        }
    }


}
