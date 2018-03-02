package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * CompulsoryCourseAdapter 必修课列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update
 */
public class CompulsoryCourseAdapter extends BaseQuickAdapter<CoursewareEntity, BaseViewHolder> {

    private Context mContext;

    public CompulsoryCourseAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_compulsory_course, DataServer.getCoursewareData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CoursewareEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_status, item.getStatus() == 0 ? "未学习" : item.getStatus() == 1 ? "已学习，未完成" : "已完成");
        helper.setText(R.id.tv_last_time, item.getDatetime());

        helper.setTextColor(R.id.tv_status, item.getStatus() == 0 ?
                mContext.getResources().getColor(R.color.colorPrimary) :
                mContext.getResources().getColor(R.color.green_400));
    }


}