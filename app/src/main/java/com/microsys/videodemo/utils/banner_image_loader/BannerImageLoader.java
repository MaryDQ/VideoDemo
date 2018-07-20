package com.microsys.videodemo.utils.banner_image_loader;

import android.content.Context;
import android.widget.ImageView;

import com.microsys.videodemo.utils.GlideUtils;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImageView(context,path,imageView);
    }
}
