package cn.walkpast.stamperlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * Author: Kern
 * Time: 2018/7/4 15:30
 * Description: This is..
 */

public class Stamper {

    private Context mContext;
    private StampManager mStampManager;

    public Stamper(Context context) {
        mContext = context;
    }

    public static Stamper with(Context context) {
        return new Stamper(context);
    }

    private Bitmap mMasterBitmap;
    private Bitmap mWatermark;
    private StampPadding mStampPadding = new StampPadding(10, 10);
    private String mLabel;
    private int mLabelSize;
    private int mLabelColor;
    private StampType mStampType;
    private StampWatcher mStampWatcher;

    public Bitmap getMasterBitmap() {
        return mMasterBitmap;
    }

    public Stamper setMasterBitmap(Bitmap masterBitmap) {
        mMasterBitmap = masterBitmap;
        return this;
    }

    public Bitmap getWatermark() {
        return mWatermark;
    }

    public Stamper setWatermark(Bitmap watermark) {
        mWatermark = watermark;
        return this;
    }

    public StampPadding getStampPadding() {
        return mStampPadding;
    }

    public Stamper setStampPadding(StampPadding stampPadding) {
        mStampPadding = stampPadding;
        return this;
    }

    public String getLabel() {
        return mLabel;
    }

    public Stamper setLabel(String label) {
        mLabel = label;
        return this;
    }

    public int getLabelSize() {
        return mLabelSize;
    }

    public Stamper setLabelSize(int labelSize) {
        mLabelSize = labelSize;
        return this;
    }

    public int getLabelColor() {
        return mLabelColor;
    }

    public Stamper setLabelColor(int labelColor) {
        mLabelColor = labelColor;
        return this;
    }

    public StampWatcher getStampWatcher() {
        return mStampWatcher;
    }

    public Stamper setStampWatcher(StampWatcher stampWatcher) {
        mStampWatcher = stampWatcher;
        return this;
    }

    public StampType getStampType() {
        return mStampType;
    }

    public Stamper setStampType(StampType stampType) {
        mStampType = stampType;
        return this;
    }

    public void build() {

        if (checkParams()) {

            if (mStampManager == null) {
                mStampManager = new StampManager(mContext, mStampWatcher);
            }
            switch (mStampType) {

                case TEXT:
                    mStampManager.stampText(mMasterBitmap, mLabel, mLabelSize, mLabelColor, mStampPadding);
                    break;
                case IMAGE:
                    mStampManager.stampImage(mMasterBitmap, mWatermark, mStampPadding);
                    break;

            }
        }
    }

    protected boolean checkParams() {

        if (mContext == null) {
            throw new NullPointerException("Context can't be null");
        }

        if (mStampType == null) {
            throw new NullPointerException("StampType can't be null");
        }

        if (mMasterBitmap == null) {
            throw new NullPointerException("MasterBitmap can't be null");
        }

        if (mStampWatcher == null) {
            throw new NullPointerException("StampWatcher can't be null");
        }

        switch (mStampType) {

            case TEXT:
                if (TextUtils.isEmpty(mLabel)) {
                    throw new NullPointerException("Label can't be null");
                }
                break;

            case IMAGE:


                if (mWatermark == null) {
                    throw new NullPointerException("Watermark can't be null");
                }

                break;
        }

        return true;
    }
}
