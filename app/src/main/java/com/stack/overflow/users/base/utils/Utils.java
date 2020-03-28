package com.stack.overflow.users.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.squareup.picasso.Picasso;
import com.stack.overflow.users.StackOverflowApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */

public class Utils {

    private Utils() {
        // Empty constructor prevent sonarqube
    }

    /**
     * Run on UI thread
     * @param runnable  The {@link Runnable}
     */
    public static void runOnUiSafeThread(Runnable runnable) {
        if (runnable != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                runnable.run();
            } else {
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }
    }

    /**
     * Format date from the unixTimestamp
     * @param unixTimestamp  The unixTimestamp
     * @return               The string of date
     */
    public static String formatDate(long unixTimestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date time = new Date(TimeUnit.MILLISECONDS.convert(unixTimestamp, TimeUnit.SECONDS));
        return dateFormat.format(time);
    }

    public static Bitmap toBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] toBytes(String imageUrl) throws IOException {
        Bitmap bitmap = new  Picasso.Builder(StackOverflowApplication.getInstance()).build().load(imageUrl).get();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }
}