package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.data.entity.QuestionaireEntitiy;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * QuestionAdapter 在线问卷列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-8
 * @update
 */
public class QuestionAdapter extends BaseQuickAdapter<QuestionaireEntitiy, BaseViewHolder> {

    private Context mContext;

    public QuestionAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_online, DataServer.getQuestionaireData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionaireEntitiy item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, "活动时间: " + item.getStart_time() + "至" + item.getEnd_time());
        helper.setText(R.id.tv_status, item.getStatus() == 0 ? "进行中" : "已结束");
    }


}
