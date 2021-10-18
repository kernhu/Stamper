package com.xcion.stamper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;


/**
 * Author: Kern
 * Time: 2018/7/4 16:42
 * Description: This is..Stamper's manager
 */

public class StampManager {

    private Bitmap newBitmap = null;
    private Canvas canvas = null;

    public void stamp(Handler handler, Stamper.Builder builder) throws Exception {

        if (builder.getStampType() == StampType.TEXT) {

            Bitmap.Config config = builder.getMasterBitmap().getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }

            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setDither(true);
            paint.setColor(builder.getLabelColor());
            paint.setTextSize(builder.getLabelSize());
            newBitmap = builder.getMasterBitmap().copy(config, true);
            canvas = new Canvas(newBitmap);
            canvas.drawText(builder.getLabel(), builder.getStampTextCoordinate().x, builder.getStampTextCoordinate().y + builder.getLabelSize(), paint);
            canvas.save();//Canvas.ALL_SAVE_FLAG;
            canvas.restore();

        } else if (builder.getStampType() == StampType.IMAGE) {

            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            newBitmap = Bitmap.createBitmap(builder.getMasterBitmap().getWidth(), builder.getMasterBitmap().getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(newBitmap);
            canvas.drawBitmap(builder.getMasterBitmap(), 0, 0, paint);
            canvas.drawBitmap(builder.getWatermark(), builder.getStampImageCoordinate().x, builder.getStampImageCoordinate().y, paint);
            canvas.save();//Canvas.ALL_SAVE_FLAG
            canvas.restore();

        } else if (builder.getStampType() == StampType.TELETEXT) {

            Paint paintText = new Paint();
            paintText.setFilterBitmap(true);
            paintText.setDither(true);
            paintText.setColor(builder.getLabelColor());
            paintText.setTextSize(builder.getLabelSize());
            Paint paintImage = new Paint();
            paintImage.setFilterBitmap(true);
            newBitmap = Bitmap.createBitmap(builder.getMasterBitmap().getWidth(), builder.getMasterBitmap().getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(newBitmap);
            canvas.drawBitmap(builder.getMasterBitmap(), 0, 0, paintImage);
            canvas.drawBitmap(builder.getWatermark(), builder.getStampImageCoordinate().x, builder.getStampImageCoordinate().y, paintImage);
            canvas.drawText(builder.getLabel(), builder.getStampTextCoordinate().x, builder.getStampTextCoordinate().y + builder.getLabelSize(), paintText);
            canvas.save();//Canvas.ALL_SAVE_FLAG
            canvas.restore();

        }

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (builder.getStampWatcher() != null) {
                        builder.getStampWatcher().onSuccess(newBitmap, builder.getRequestId());
                    }
                }
            });
        }
    }
}
