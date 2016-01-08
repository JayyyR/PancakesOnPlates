package com.joeracosta.library;

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

    private Bundle mDataToPass;

    public View createView(Context context, ViewGroup container) {
        View view = LayoutInflater.from(context).inflate(getLayoutResource(), container, false);

        //if our view is a screen, pass our bundle to it
        if (view instanceof Screen && mDataToPass != null) {
            ((Screen) view).setPassedData(mDataToPass);
        }
        deleteDataToPass();
        return view;
    }

    /**
     * Convenience method that a can be called pass data to a screen this ViewFactory represents.
     * The view must be an instance of Screen to get this data. Call getPassedData() in the Screen to
     * retrieve this bundle.
     * @param data the bundle of data you want to pass
     */
    public void passData(Bundle data){
        mDataToPass = data;
    }

    public Bundle getDataToPass(){
        return mDataToPass;
    }

    public void deleteDataToPass(){
        mDataToPass = null;
    }

    /**
     * @return the layout resource of the view you want to inflate
     */
    public abstract int getLayoutResource();

}
