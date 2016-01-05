package me.mattlogan.library;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Joe on 1/4/2016.
 */
public abstract class Screen extends FrameLayout{

    private static String INSTANCE_STATE = "com.joeracosta.instanceState";
    private boolean mRestored;

    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected boolean isRestored(){
        return mRestored;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getId() == 0){
            //Todo throw exception, need ID
        }
        onScreenAttached();
    }

    // Note: This won't be called when we push the next View onto the stack because this View is
    // kept in the container's view hierarchy. It's visibility is just set to gone.
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onScreenGone();
        onScreenDetatched();
    }

    protected void onScreenAttached(){

    }

    protected void onScreenDetatched(){

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
            case INVISIBLE:
                if (changedView == this || getVisibility() != INVISIBLE) {
                    onScreenInvisible();
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
     * Called when screen becomes invisible. This is less likely
     */
    protected void onScreenInvisible(){

    }

    /**
     * Called when screen is gone from view or detached. The screen still might exist and be attached, but
     * it is not visible
     */
    protected void onScreenGone(){

    }

}
