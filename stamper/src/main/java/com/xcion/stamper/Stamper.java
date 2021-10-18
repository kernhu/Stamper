package com.xcion.stamper;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Kern
 * Time: 2018/7/4 15:30
 * Description: This is..
 */

public class Stamper {

    private static StampManager mStampManager = new StampManager();
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private static Stamper.Builder mBuilder;

    public static Stamper.Builder with() {
        synchronized (Stamper.class) {
            if (mBuilder == null) {
                mBuilder = new Stamper.Builder();
            }
        }
        return mBuilder;
    }

    public static void onDestroy() {

        if (mExecutorService != null) {
            mExecutorService.shutdownNow();
            mExecutorService = null;
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        if (mStampManager != null) {
            mStampManager = null;
        }

        if (mBuilder != null) {
            mBuilder = null;
        }

    }

    public Stamper(Builder builder) {
        mExecutorService.execute(new WorkerRunnable(mHandler, builder));
    }


    public static class Builder {

        private Bitmap masterBitmap;
        private Bitmap watermark;
        private StampCoordinate stampTextCoordinate = new StampCoordinate(10, 10);
        private StampCoordinate stampImageCoordinate = new StampCoordinate(10, 10);
        private String label;
        private int labelSize;
        private int labelColor;
        private int requestId;
        private StampType stampType;
        private StampWatcher stampWatcher;

        public Bitmap getMasterBitmap() {
            return masterBitmap;
        }

        public Builder setMasterBitmap(Bitmap masterBitmap) {
            this.masterBitmap = masterBitmap;
            return this;
        }

        public Bitmap getWatermark() {
            return watermark;
        }

        public Builder setWatermark(Bitmap watermark) {
            this.watermark = watermark;
            return this;
        }

        public StampCoordinate getStampTextCoordinate() {
            return stampTextCoordinate;
        }

        public Builder setStampTextCoordinate(StampCoordinate stampTextCoordinate) {
            this.stampTextCoordinate = stampTextCoordinate;
            return this;
        }

        public StampCoordinate getStampImageCoordinate() {
            return stampImageCoordinate;
        }

        public Builder setStampImageCoordinate(StampCoordinate stampImageCoordinate) {
            this.stampImageCoordinate = stampImageCoordinate;
            return this;
        }

        public String getLabel() {
            return label;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public int getLabelSize() {
            return labelSize;
        }

        public Builder setLabelSize(int labelSize) {
            this.labelSize = labelSize;
            return this;
        }

        public int getLabelColor() {
            return labelColor;
        }

        public Builder setLabelColor(int labelColor) {
            this.labelColor = labelColor;
            return this;
        }

        public int getRequestId() {
            return requestId;
        }

        public Builder setRequestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public StampType getStampType() {
            return stampType;
        }

        public Builder setStampType(StampType stampType) {
            this.stampType = stampType;
            return this;
        }

        public StampWatcher getStampWatcher() {
            return stampWatcher;
        }

        public Builder setStampWatcher(StampWatcher stampWatcher) {
            this.stampWatcher = stampWatcher;
            return this;
        }

        public Stamper build() {
            return new Stamper(this);
        }
    }

    /**
     * check param
     *
     * @return
     */
    protected boolean checkParams(Builder builder) {

        if (builder == null) {
            return false;
        }

        if (builder.stampType == null) {
            throw new NullPointerException("StampType can't be null,please set a value for StampType.");
        }

        if (builder.masterBitmap == null) {
            throw new NullPointerException("MasterBitmap can't be null,please set a value for MasterBitmap.");
        }

        if (builder.stampWatcher == null) {
            throw new NullPointerException("StampWatcher can't be null,please set a value for StampWatcher.");
        }

        switch (builder.stampType) {

            case TEXT:
                if (TextUtils.isEmpty(builder.label)) {
                    throw new NullPointerException("Label can't be null,please set a value for Label.");
                }
                break;

            case IMAGE:

                if (builder.watermark == null) {
                    throw new NullPointerException("Watermark can't be null,please set a value for Watermark.");
                }

                break;
            default:

                if (TextUtils.isEmpty(builder.label)) {
                    throw new NullPointerException("Label can't be null,please set a value for Label.");
                }
                if (builder.watermark == null) {
                    throw new NullPointerException("Watermark can't be null,please set a value for Watermark.");
                }

                break;
        }

        return true;
    }

    private class WorkerRunnable implements Runnable {

        private Handler handler;
        private Builder builder;

        public WorkerRunnable(Handler handler, Builder builder) {
            this.handler = handler;
            this.builder = builder;
        }

        @Override
        public void run() {

            if (checkParams(builder)) {
                try {
                    mStampManager.stamp(handler, builder);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (handler != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (builder.getStampWatcher() != null) {
                                    builder.getStampWatcher().onError(e.getMessage(), builder.getRequestId());
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
