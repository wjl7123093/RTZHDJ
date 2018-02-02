package com.mytv.rtzhdj.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.R;

/**
 * ImageLoader 图片加载类(内置 xutils3)
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2018-1-19
 * @update 2018-1-25 新增 图片加载库(Picasso)
 *         2018-1-29 改为 图片加载库(Glide)
 */
public class ImageLoader {

    private static ImageLoader instance;

    public ImageLoader() {}

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 显示图片（Glide）
     * @param v
     * @param imgUrl
     */
    public void showImage(Context context, ImageView v, String imgUrl) {
//        Picasso.with(context).load(imgUrl).into(v);
        ArmsUtils.obtainAppComponentFromContext(context).imageLoader().loadImage(context,
                ImageConfigImpl
                        .builder()
                        .errorPic(R.mipmap.ic_error)
                        .placeholder(R.mipmap.ic_placeholder)
                        .url(imgUrl)
                        .imageView(v)
                        .build());
    }

}
