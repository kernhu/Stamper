package com.xcion.stamper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author: Kern
 * Time: 2018/7/4 16:42
 * Description: This is..Stamper's manager
 */

public class StampManager {

    private Context mContext;
    private StampWatcher mStampWatcher;

    public StampManager(Context context, StampWatcher watcher) {
        mContext = context;
        mStampWatcher = watcher;
    }

    /**
     * draw image
     *
     * @param masterBitmap
     * @param watermark
     * @param padding
     */
    public void stampImage(Bitmap masterBitmap, Bitmap watermark, StampPadding padding, int requestId) {

        int width = masterBitmap.getWidth();
        int height = masterBitmap.getHeight();

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        Bitmap newBitmap = null;
        Canvas canvas = null;

        try {
            newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            canvas = new Canvas(newBitmap);
            canvas.drawBitmap(masterBitmap, 0, 0, paint);

            canvas.drawBitmap(watermark, padding.left, padding.top, paint);
            canvas.save();//Canvas.ALL_SAVE_FLAG
            canvas.restore();

            if (mStampWatcher != null) {
                mStampWatcher.onSuccess(newBitmap, requestId);
            }
        } catch (Exception e) {

            if (mStampWatcher != null) {
                mStampWatcher.onError(e.getMessage(), requestId);
            }
        }
    }

    /**
     * draw text
     *
     * @param masterBitmap
     * @param label
     * @param labelSize
     * @param labelColor
     * @param padding
     */
    public void stampText(Bitmap masterBitmap, String label, int labelSize, int labelColor, StampPadding padding, int requestId) {

        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(labelColor);
        paint.setTextSize(labelSize);

        Bitmap newBitmap = null;
        Canvas canvas = null;
        try {
            Bitmap.Config config = masterBitmap.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            newBitmap = masterBitmap.copy(config, true);
            canvas = new Canvas(newBitmap);
            canvas.drawText(label, padding.left, padding.top, paint);
            canvas.save();//Canvas.ALL_SAVE_FLAG;
            canvas.restore();
            if (mStampWatcher != null) {
                mStampWatcher.onSuccess(newBitmap, requestId);
            }
        } catch (Exception e) {

            if (mStampWatcher != null) {
                mStampWatcher.onError(e.getMessage(), requestId);
            }
        }
    }
}
