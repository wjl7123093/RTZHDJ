package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.app.data.entity.TestingEntity;

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

    }


}
