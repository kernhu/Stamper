package cn.walkpast.stamperlib;

import android.graphics.Bitmap;

/**
 * Author: Kern
 * Time: 2018/7/4 15:27
 * Description: This is..
 */

public abstract class StampWatcher {


    protected void onSuccess(Bitmap bitmap, int requestId) {


    }

    protected void onError(String error, int requestId) {
    }
}
