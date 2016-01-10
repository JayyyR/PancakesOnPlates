package com.joeracosta.library;

/**
 * Created by Joe on 1/4/2016.
 * interface to use if you want Views responding to a back press.
 */
public interface BackPressListener {
    /**
     *
     * @return true if back press is handled, false if not
     */
    boolean onBackPressed();
}
