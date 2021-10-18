package com.xcion.stamper;

import android.graphics.Bitmap;

/**
 * Author: Kern
 * Time: 2018/7/4 15:27
 * Description: This is.. listener
 */

public abstract class StampWatcher {

    /**
     * success
     *
     * @param bitmap    the result bitmap
     * @param requestId this id is used to differentiate the request
     */
    protected void onSuccess(Bitmap bitmap, int requestId) {

    }

    /**
     * fail
     *
     * @param error     the result bitmap
     * @param requestId this id is used to differentiate the request
     */
    protected void onError(String error, int requestId) {

    }

}
