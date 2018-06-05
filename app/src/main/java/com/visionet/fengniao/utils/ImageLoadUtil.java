package com.visionet.fengniao.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.visionet.fengniao.R;

public class ImageLoadUtil {
    public static void loadImage(Context context, String url, ImageView imageView) {
        imageView.setImageResource( R.mipmap.img_default );
        if (url != null) {
            Glide.with( context ).load(url )
                    .error( R.mipmap.img_default )
                    .placeholder( R.mipmap.img_default )
                    .into( imageView );
        }
    }
    public static void load(Context context, String url, ImageView imageView) {
        if (url != null) {
            Glide.with( context ).load(url ).into( imageView );
        }
    }
}
