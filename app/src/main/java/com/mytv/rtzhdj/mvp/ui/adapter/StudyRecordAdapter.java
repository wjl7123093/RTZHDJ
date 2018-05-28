package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.StudyRecordEntity;

import java.util.List;

/**
 * StudyRecordAdapter 学习记录列表Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-7
 * @update
 */
public class StudyRecordAdapter extends BaseQuickAdapter<StudyRecordEntity, BaseViewHolder> {

    private Context mContext;

    public StudyRecordAdapter(Context context, List<StudyRecordEntity> studyRecordList) {
        super(R.layout.item_rv_study_record, studyRecordList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StudyRecordEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, "学习时间: " + item.getLastStudyTime());
        helper.setText(R.id.tv_type, item.getTypeString());
        helper.setText(R.id.tv_scores, "积分数量: +" + item.getScore());
    }


}
