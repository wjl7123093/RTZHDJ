package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.TestingEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * TestingAdapter 评论列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update 2018-2-28    新增路由 成绩排行按钮跳转
 */
public class TestingAdapter extends BaseQuickAdapter<TestingEntity, BaseViewHolder> {

    private Context mContext;

    public TestingAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_effect_evaluation, DataServer.getTestingData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TestingEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_scores, "分数:" + item.getScores() + "　　剩余:" +
                item.getLast_num() + "/" + item.getTotal_num());
        helper.setText(R.id.tv_test_time, item.getStart_time() + "至" + item.getEnd_time());
        helper.setText(R.id.tv_last_time, "最近测试时间: " + item.getLast_time());

        helper.getView(R.id.tv_grade_rank).setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_GRADE_RANK).navigation();
        });

    }


}
