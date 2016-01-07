package me.mattlogan.library;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Interface for deferred creation of View instances.
 */
public abstract class ViewFactory implements Serializable {

    private Bundle mPassedData;

    public View createView(Context context, ViewGroup container) {
        View view = LayoutInflater.from(context).inflate(getLayoutResource(), container, false);

        //if our view is a screen, pass our bundle to it
        if (view instanceof Screen && mPassedData != null) {
            ((Screen) view).setPassedData(mPassedData);
        }
        mPassedData = null;
        return view;
    }

    /**
     * Convenience method that a can be called pass data to a screen this ViewFactory represents.
     * The view must be an instance of Screen to get this data. Call getPassedData() in the Screen to
     * retrieve this bundle.
     * @param data the bundle of data you want to pass
     */
    public void passData(Bundle data){
        mPassedData = data;
    }

    /**
     * @return the layout resource of the view you want to inflate
     */
    public abstract int getLayoutResource();

    public Bundle getPassedData(){
        return mPassedData;
    }
}
