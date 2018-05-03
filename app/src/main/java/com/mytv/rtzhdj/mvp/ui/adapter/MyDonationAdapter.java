package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * MyDonationAdapter 我的捐赠列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-3
 * @update
 */
public class MyDonationAdapter extends BaseQuickAdapter<MyDonateEntity, BaseViewHolder> {

    private Context mContext;

    public MyDonationAdapter(Context context, List<MyDonateEntity> donationList) {
        super(R.layout.item_rv_my_donation, donationList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyDonateEntity item) {
        helper.setText(R.id.tv_topic, item.getTopic());
        helper.setText(R.id.tv_phone, item.getPhone());

        if (!TextUtils.isEmpty(item.getImgurl())) {
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
                    Api.APP_IMAGE_DOMAIN + item.getImgurl().replace("@", ""));
        }
    }


}
