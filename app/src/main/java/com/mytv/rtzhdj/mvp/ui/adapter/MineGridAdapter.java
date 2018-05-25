package com.mytv.rtzhdj.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.widget.IconView;

import java.util.List;
import java.util.Map;

/**
 * Created by ColorApple on 2018/5/25.
 */

public class MineGridAdapter extends BaseAdapter {

    private Context mContext = null;
    private List<? extends Map<String, ?>> mData = null;

    public MineGridAdapter(Context context, List<? extends Map<String, ?>> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_vlayout_mine_grid, null);
            viewHolder = new ViewHolder();
            viewHolder.mIcGrid = convertView.findViewById(R.id.ic_grid);
            viewHolder.mTvTitle = convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mIcGrid.setText(mData.get(position).get("image").toString());
        viewHolder.mTvTitle.setText(mData.get(position).get("title").toString());

        return convertView;
    }

    final class ViewHolder {
        IconView mIcGrid;
        TextView mTvTitle;
    }
}
