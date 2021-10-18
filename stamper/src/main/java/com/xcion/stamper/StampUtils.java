package com.xcion.stamper;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

public class StampUtils {


    /**
     * Get the width and height of text;
     *
     * @param text
     * @return 0 width ,1 height
     */
    public static int[] getTextWidthAndHeight(String text) {
        if (text == null) {
            throw new NullPointerException("text could not be null");
        }
        int[] size = new int[2];
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        size[0] = rect.width();
        size[1] = rect.height();
        return size;
    }

    /**
     * Get the width and height of bitmap;
     *
     * @param bitmap
     * @return 0 width ,1 height
     */
    public static int[] getImageWidthAndHeight(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("bitmap could not be null");
        }
        int[] size = new int[2];
        size[0] = bitmap.getWidth();
        size[1] = bitmap.getHeight();
        return size;
    }

}
