package com.xcion.stamper;

/**
 * Author: Kern
 * Time: 2018/7/4 17:01
 * Description: This is.. the padding of image or text
 */

public class StampCoordinate {

    /**
     * The x-coordinate of the origin of the text being drawn
     */
    public float x;
    /**
     * The y-coordinate of the baseline of the text being drawn
     */
    public float y;

    public StampCoordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
