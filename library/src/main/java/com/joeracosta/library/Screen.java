package com.joeracosta.library;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.joeracosta.library.Map.NoViewIDException;

/**
 * Created by Joe on 1/4/2016.
 * Abstract Class that adds convenience methods to a View. A Screen should be though of as one Screen
 * on the device. It can contain one view. It can contain many custom views. The implemenation
 * of this class should contain a ViewFactory so it can be properly added to a ViewStack or ViewMap.
 */
public abstract class Screen extends FrameLayout {

    private static String INSTANCE_STATE = "com.joeracosta.screen.instanceState";
    private boolean mRestored;
    private Bundle mPassedData;

    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
        setId(getViewId());
    }

    /**
     * Call this when you want to give your Screen some data
     * @param dataToPass the Bundle of data you're passing over
     */
    public void setPassedData(Bundle dataToPass){
        mPassedData = dataToPass;
    }

    /**
     * Call this to get the data that was passed to this Screen. Once you call this
     * the data is cleared. In other words, two consecutive calls to this will yield the appropriate
     * bundle and null respectively.
     * @return the data that was passed to this Screen
     */
    public Bundle getPassedData(){
        Bundle tempData = mPassedData;
        mPassedData = null;
        return tempData;
    }

    protected boolean isRestored(){
        return mRestored;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getId() == View.NO_ID){
            try {
                throw new NoViewIDException("Your Screen must set a unique ID, returned in the getViewID() method");
            } catch (NoViewIDException e) {
                e.printStackTrace();
            }
        }
        onScreenAttached();
    }

    // Note: This won't be called when we push the next View onto the stack because this View is
    // kept in the container's view hierarchy. It's visibility is just set to gone.
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onScreenGone();
        onScreenDetached();
    }

    /**
     * Called when a screen is attached to the window.
     */
    protected void onScreenAttached(){
    }

    /**
     * Must return a unique view id. This is the id that the Screen's view will be set to as soon as it's created.
     * Best practice would be to define your screen IDs in your values resource folder and use those.
     * @return a unique id for this Screen
     */
    public abstract int getViewId();

    /**
     * Called when a Screen is detached from the window. This won't necessarily be called
     * just because a Screen is no longer visible to the user.
     */
    protected void onScreenDetached(){

    }

    // Note: These instance state saving methods will only be called if the view has an id.
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        return onSaveState(bundle);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable savedInstanceState) {
        if (savedInstanceState instanceof Bundle) {
            Bundle bundle = (Bundle) savedInstanceState;
            mRestored = true;
            onRestoreState(bundle);
            savedInstanceState = bundle.getParcelable(INSTANCE_STATE);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Called when screen is destroyed and is saving state
     * @param bundle the bundle to return. Add what you want to this before returning it back.
     * @return
     */
    protected Bundle onSaveState(Bundle bundle){
        return bundle;
    }

    /**
     * Called when screen is restored with data
     * @param bundle The bundle with saved stuff. Grab your stuff from this bundle.
     */
    protected void onRestoreState(Bundle bundle){
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        switch (visibility){
            case VISIBLE:
                if (changedView == this || getVisibility() == VISIBLE) {
                    onScreenVisible();
                }
                break;
            case GONE:
                if (changedView == this || getVisibility() != GONE) {
                    onScreenGone();
                }
                break;
        }

        super.onVisibilityChanged(changedView, visibility);

    }

    /**
     * Called when screen becomes visible on screen. The screen might have been created or brought
     * back into view
     */
    protected void onScreenVisible(){

    }

    /**
     * Called when screen is gone from view or detached. The screen still might exist and be attached, but
     * it is not visible
     */
    protected void onScreenGone(){
        mPassedData = null;

    }

}
