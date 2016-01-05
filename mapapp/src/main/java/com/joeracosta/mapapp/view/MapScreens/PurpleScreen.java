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

    public static class Factory implements ViewFactory {
        @Override
        public View createView(Context context, ViewGroup container) {

            //todo check if getLayout id is 0
            return LayoutInflater.from(context).inflate(R.layout.purple_screen, container, false);
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
    protected void onScreenDetatched() {
        Log.d("testing", "PurpleView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetatched();
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
