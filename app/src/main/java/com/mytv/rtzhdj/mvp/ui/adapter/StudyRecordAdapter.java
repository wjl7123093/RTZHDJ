package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * StudyRecordAdapter 学习记录列表Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-7
 * @update
 */
public class StudyRecordAdapter extends BaseQuickAdapter<CoursewareEntity, BaseViewHolder> {

    private Context mContext;

    public StudyRecordAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_study_record, DataServer.getCoursewareData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CoursewareEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_datetime, item.getDatetime());
        helper.setText(R.id.tv_type, item.getType() == 1 ? "必修课"
                : item.getType() == 2 ? "选修课" : "微党课");
        helper.setText(R.id.tv_scores, item.getScores() + "");
    }


}
