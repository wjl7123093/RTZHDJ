package com.mytv.rtzhdj.app.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.youth.banner.loader.ImageLoader;

//import org.xutils.x;

/**
 * BannerImageLoader banner 图片加载类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-22
 * @update 2018-1-25    更换为 图片加载库(Picasso)
 *         2018-1-29    更换为 图片加载库(Glide)
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

        /*ArmsUtils.obtainAppComponentFromContext(context).imageLoader().loadImage(context,
                ImageConfigImpl
                        .builder()
                        .url((String) path)
                        .imageView(imageView)
                        .build());*/

        if (!TextUtils.isEmpty((String) path))
            com.mytv.rtzhdj.app.utils.ImageLoader.getInstance().showImage(context, imageView, (String) path);

    }

    @Override
    public ImageView createImageView(Context context) {
        /*//使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;*/
        return super.createImageView(context);
    }
}
