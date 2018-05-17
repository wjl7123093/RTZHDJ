package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.EffectEvaluationEntity;

import java.util.List;

/**
 * EffectEvaluationAdapter 效果评测列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-26
 * @update
 */
public class EffectEvaluationAdapter extends BaseQuickAdapter<EffectEvaluationEntity, BaseViewHolder> {

    private Context mContext;

    public EffectEvaluationAdapter(Context context, List<EffectEvaluationEntity> effectList) {
        super(R.layout.item_rv_effect_evaluation, effectList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, EffectEvaluationEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_scores, "分数:" + item.getScore() + "　　剩余:" +
                item.getExaminationCount() + "/" + item.getTestAlready());
        helper.setText(R.id.tv_test_time, item.getStartTime().split("T")[0] + " 至 "
                + item.getEndTime().split("T")[0]);
        helper.setText(R.id.tv_last_time, "最近测试时间: " + item.getLastStudyTime());

        helper.getView(R.id.tv_grade_rank).setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_GRADE_RANK)
                    .withInt("examinationId", item.getId()).navigation();
        });

    }


}
