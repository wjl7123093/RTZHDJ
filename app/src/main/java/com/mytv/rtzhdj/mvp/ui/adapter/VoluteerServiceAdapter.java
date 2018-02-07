package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;

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

    public VoluteerServiceAdapter(Context context, int dataSize) {
        super(R.layout.item_vlayout_list_event2, DataServer.getVolunteerServiceData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoluteerServiceEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_deadtime, "截止日期:" + item.getDeadtime());
        helper.setText(R.id.tv_star_num, item.getStar_num() + "");
        helper.setText(R.id.tv_comment_num, item.getComment_num() + "");
        helper.setText(R.id.tv_join_num, "参与人数: " + item.getJoin_num() + "/" + item.getTotal_num());
        helper.setText(R.id.tv_star_num, item.getStar_num() + "");
        helper.setText(R.id.tv_status, item.getStatus() == 0 ? "正在进行" : "已结束");
    }


}
