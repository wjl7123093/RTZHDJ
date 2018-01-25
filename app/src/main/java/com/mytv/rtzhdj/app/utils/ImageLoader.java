package com.mytv.rtzhdj.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.mytv.rtzhdj.R;
import com.squareup.picasso.Picasso;

//import org.xutils.image.ImageOptions;
//import org.xutils.x;

/**
 * ImageLoader 图片加载类(内置 xutils3)
 *
 * @author Fred_W
 * @version v1.0.0
 *
 * @crdate 2018-1-19
 * @update 2018-1-25 新增 Passico 加载图片方法
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
     * 初始化 imageOptions
     * @return
     */
    /*private ImageOptions initImageOptions() {
        //通过ImageOptions.Builder().set方法设置图片的属性
        ImageOptions imageOptions= new ImageOptions.Builder()
                //ImageOptions.Builder()的一些其他属性：
                .setFadeIn(true)    // 淡入效果
//                .setCircular(true) //设置图片显示为圆形
                .setSquare(true) //设置图片显示为正方形
                .setCrop(true)
//                .setSize(200,200) //设置大小
//                .setAnimation(animation) //设置动画
//                .setFailureDrawable(Drawable failure／Drawable) //设置加载失败的动画
                .setFailureDrawableId(R.mipmap.ic_launcher) //以资源id设置加载失败的动画
//                .setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.mipmap.ic_launcher) //以资源id设置加载中的动／画
                .setIgnoreGif(false) //忽略Gif图片
//                .setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                .setRadius(0) //设置拐角弧度
                .setUseMemCache(true) //设置使用MemCache，默认true
                .build();

        return imageOptions;
    }*/

    /**
     * 显示图片
     * @param v
     * @param imgUrl
     */
    /*public void showImage(ImageView v, String imgUrl) {
        x.image().bind(v, imgUrl, initImageOptions());
    }*/

    /**
     * 显示图片（Passico）
     * @param v
     * @param imgUrl
     */
    public void showImage(Context context, ImageView v, String imgUrl) {
        Picasso.with(context).load(imgUrl).into(v);
    }

}
