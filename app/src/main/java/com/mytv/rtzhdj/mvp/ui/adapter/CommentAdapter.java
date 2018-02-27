package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;

/**
 * CommentAdapter 评论列表 Adapter
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update
 */
public class CommentAdapter extends BaseQuickAdapter<CommentEntity, BaseViewHolder> {

    private Context mContext;

    public CommentAdapter(Context context, int dataSize) {
        super(R.layout.item_rv_comment, DataServer.getCommentData(dataSize));
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_time, item.getDatetime());
        helper.setText(R.id.tv_star_num, item.getStar_num() + "");
        helper.setText(R.id.tv_content, item.getContent() + "");

        ImageLoader.getInstance().showImage(mContext, helper.getView(R.id.iv_header), item.getImg_url());
    }


}
