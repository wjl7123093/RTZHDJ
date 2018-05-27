package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;

import java.util.List;

/**
 * CompulsoryCourseAdapter 必修课列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update 2018-4-25    对接接口修改构造方法
 */
public class CompulsoryCourseAdapter extends BaseQuickAdapter<CoursewareEntity, BaseViewHolder> {

    private Context mContext;

    public CompulsoryCourseAdapter(Context context, List<CoursewareEntity> courseList) {
        super(R.layout.item_rv_compulsory_course, courseList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CoursewareEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_status, item.getCourseTypeString());
        helper.setText(R.id.tv_last_time, item.getLastStudyTime());

        // 1 已学习，2 未学习
        helper.setTextColor(R.id.tv_status, item.getCourseType() == 1 ?
                mContext.getResources().getColor(R.color.colorPrimary) :
                mContext.getResources().getColor(R.color.green_400));
    }


}
