package com.example.n_one.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

//import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class FileUtils {

    private static final String TAG = "FileUtils";


    public static String getFilenameFromURI(Context packageContext, Uri contentUri) throws FileNotFoundException {
        String path = getPathFromURI(packageContext, contentUri);
        int slashIndex = path.lastIndexOf("/") + 1;
        String fileName = path.substring(slashIndex);
        return fileName;
    }

    public static String getPathFromURI(Context packageContext, Uri contentUri) throws FileNotFoundException {

        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        ParcelFileDescriptor fileDescriptor = packageContext.getContentResolver().openFileDescriptor(contentUri, "r");

//        Cursor cursor =packageContext.getContentResolver().openFileDescriptor(contentUri, "r");

        Cursor cursor = packageContext.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;

    }


//    public static File createImageFile(Context context) throws IOException {
//
//        String fileName = generateNewImageName();
//        File filesDir = context.getFilesDir();
//        return new File(filesDir,
//                fileName);
//
//    }
//
//    public static String generateNewImageName() {
//        Date date = LocalDateTime.now().toDate();
//        String imageName = DateUtil.getDateTimeMillisFormatted(date) + ".jpg";
//        return imageName;
//    }


}
