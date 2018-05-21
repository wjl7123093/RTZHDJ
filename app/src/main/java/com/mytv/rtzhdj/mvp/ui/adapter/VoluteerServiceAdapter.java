package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * VolunteerServiceAdapter 志愿服务列表Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-7
 * @update
 */
public class VoluteerServiceAdapter extends BaseQuickAdapter<VoluteerServiceEntity, BaseViewHolder> {

    private Context mContext;

    public VoluteerServiceAdapter(Context context, List<VoluteerServiceEntity> serviceList) {
        super(R.layout.item_vlayout_list_event2, serviceList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoluteerServiceEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_deadtime, "截止日期:" + item.getEnrollEndDate());
//        helper.setText(R.id.tv_star_num, item.get() + "");
//        helper.setText(R.id.tv_comment_num, item.getComment_num() + "");
        helper.setText(R.id.tv_join_num, "参与人数: " + item.getSignedup() + "/" + item.getEnrollCount());
        helper.setText(R.id.tv_status, item.isEnd() ? "已结束" : "正在进行");

//        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
//                Api.APP_IMAGE_DOMAIN + item.getImgUrl().replace("@", ""));
        if (!TextUtils.isEmpty(item.getAllImgUrl()))
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_image),
                    item.getAllImgUrl());
    }


}
