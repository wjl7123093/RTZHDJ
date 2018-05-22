package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.QuestionOnlineEntity;

import java.util.List;

/**
 * QuestionAdapter 在线问卷列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-8
 * @update 2018-5-16    对接 getSurveyList 接口
 */
public class QuestionAdapter extends BaseQuickAdapter<QuestionOnlineEntity, BaseViewHolder> {

    private Context mContext;

    public QuestionAdapter(Context context, List<QuestionOnlineEntity> questionList) {
        super(R.layout.item_rv_online, questionList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionOnlineEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, "问卷时间: " + item.getStartDate().split("T")[0]
                + "至" + item.getEndDate().split("T")[0]);
        helper.setText(R.id.tv_status, item.getStat() == 1 ? "进行中" : "已结束");
    }


}
