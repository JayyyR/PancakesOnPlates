package me.mattlogan.library;

/**
 * Created by Joe on 1/4/2016.
 */
public interface BackPressListener {
    /**
     *
     * @return true if back press is handled, false if not
     */
    boolean onBackPressed();
}
