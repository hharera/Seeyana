package com.example.n_one.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

public class ImageUtil {
    public static final byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }


    public static Bitmap getScaledBitmap(String path,
                                         int destWidth, int destHeight) {
// Read in the dimensions of the image on disk
        BitmapFactory.Options options = new
                BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
// Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth >
                destWidth) {
            float heightScale = srcHeight /
                    destHeight;
            float widthScale = srcWidth / destWidth;
            inSampleSize = Math.round(heightScale >
                    widthScale ? heightScale :
                    widthScale);
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
// Read in and create final bitmap
        return BitmapFactory.decodeFile(path,
                options);
    }

    public static Bitmap getBitmapFromUri(Context packageContext, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                packageContext.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;

    }
}
