package com.joeracosta.mapapp.view.MapScreens;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.joeracosta.mapapp.R;

import me.mattlogan.library.Screen;
import me.mattlogan.library.ViewFactory;

public class WhiteScreen extends Screen {

    public static class Factory implements ViewFactory {
        @Override
        public View createView(Context context, ViewGroup container) {
            return LayoutInflater.from(context).inflate(R.layout.screen_blue_map, container, false);
        }
    }

    public WhiteScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "WhiteScreen (" + hashCode() + ") created");
    }


    @Override
    protected void onScreenAttached() {
        Log.d("testing", "WhiteView (" + hashCode() + ") onAttachedToWindow");
        super.onScreenAttached();
    }

    @Override
    protected void onScreenDetatched() {
        Log.d("testing", "WhiteView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetatched();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "WhiteView (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "WhiteView (" + hashCode() + ") onRestoreInstanceState");
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "WhiteView (" + hashCode() + ") VISIBLE");
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "WhiteView (" + hashCode() + ") GONE");
    }
}
