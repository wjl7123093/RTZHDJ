package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.TeacherEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * TeacherListAdapter 师资库页面 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-28
 * @update
 */
public class TeacherListAdapter extends BaseQuickAdapter<TeacherEntity, BaseViewHolder> {

    private Context mContext;

    public TeacherListAdapter(Context context, List<TeacherEntity> newsList) {
        super(R.layout.item_rv_teacher, newsList);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, TeacherEntity item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_phone, item.getTelePhone());

        if (!TextUtils.isEmpty(item.getAllImgUrl())) {
            ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header),
                    item.getAllImgUrl());
            helper.getView(R.id.iv_header).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_header).setVisibility(View.GONE);
        }

    }
}
