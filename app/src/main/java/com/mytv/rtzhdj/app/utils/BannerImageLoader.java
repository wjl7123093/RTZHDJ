package com.mytv.rtzhdj.app.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.ArmsUtils;
import com.youth.banner.loader.ImageLoader;

import org.xutils.x;

/**
 * BannerImageLoader banner 图片加载类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-22
 * @update
 */
public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /*//Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);

        //Picasso 加载图片简单用法
        Picasso.with(context).load(path).into(imageView);

        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);*/

        com.mytv.rtzhdj.app.utils.ImageLoader.getInstance().showImage(imageView, (String)path);

    }

    @Override
    public ImageView createImageView(Context context) {
        /*//使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;*/
        return super.createImageView(context);
    }
}
