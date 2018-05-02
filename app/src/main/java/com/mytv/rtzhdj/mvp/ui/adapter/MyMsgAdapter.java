package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.MyMsgEntity;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

import java.util.List;

/**
 * MyMsgAdapter 私信列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-2
 * @update
 */
public class MyMsgAdapter extends BaseQuickAdapter<MyMsgEntity, BaseViewHolder> {

    private Context mContext;

    public MyMsgAdapter(Context context, List<MyMsgEntity> msgList) {
        super(R.layout.item_rv_mymsg, msgList);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyMsgEntity item) {
        helper.setText(R.id.tv_title, item.getMessageTopic());
        helper.setText(R.id.tv_datetime, item.getMessageTime());

    }
}
