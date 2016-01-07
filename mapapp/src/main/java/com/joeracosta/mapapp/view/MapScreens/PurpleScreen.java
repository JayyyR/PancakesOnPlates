package com.joeracosta.mapapp.view.MapScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joeracosta.mapapp.R;

import me.mattlogan.library.Screen;
import me.mattlogan.library.ViewFactory;

public class PurpleScreen extends Screen{

    public static class Factory extends ViewFactory {
        @Override
        public int getLayoutResource() {
            return R.layout.purple_screen;
        }
    }

    public PurpleScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onScreenAttached() {
        Log.d("testing", "PurpleView (" + hashCode() + ") onAttachedToWindow");
        super.onScreenAttached();
    }

    @Override
    public int getViewId() {
        return R.id.purple_screen;
    }

    @Override
    protected void onScreenDetached() {
        Log.d("testing", "PurpleView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetached();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "PurpleView (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "PurpleView (" + hashCode() + ") onRestoreInstanceState");
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "PurpleView (" + hashCode() + ") VISIBLE");
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "PurpleView (" + hashCode() + ") GONE");
    }
}
