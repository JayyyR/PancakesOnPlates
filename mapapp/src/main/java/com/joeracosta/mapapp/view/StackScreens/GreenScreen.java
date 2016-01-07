package com.joeracosta.mapapp.view.StackScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joeracosta.mapapp.R;

import me.mattlogan.library.Screen;
import me.mattlogan.library.Stack.ViewStackHost;
import com.joeracosta.mapapp.animation.CircularHide;
import com.joeracosta.mapapp.animation.CircularReveal;

import me.mattlogan.library.ViewFactory;
import me.mattlogan.library.Stack.ViewStack;

public class GreenScreen extends Screen {

    public static class Factory extends ViewFactory {
        @Override
        public int getLayoutResource() {
            return R.layout.green_screen;
        }
    }

    public GreenScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "GreenView (" + hashCode() + ") created");
    }


    @Override
    protected void onScreenAttached() {
        Log.d("testing", "greenview (" + hashCode() + ") onAttachedToWindow");
        super.onScreenAttached();

        final ViewStack viewStack = ((ViewStackHost) getParent()).getViewStack();

        findViewById(R.id.green_button_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing", "GreenView popping itself");
                viewStack.popWithAnimation(new CircularHide());
            }
        });

        findViewById(R.id.green_button_go_to_blue).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing", "GreenView pushing BlueView");
                viewStack.pushWithAnimation(new BlueScreen.Factory(), new CircularReveal());
            }
        });
    }

    @Override
    public int getViewId() {
        return R.id.green_screen;
    }

    @Override
    protected void onScreenDetached() {
        Log.d("testing", "greenview (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetached();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "greenview (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "greenview (" + hashCode() + ") onRestoreInstanceState");
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "greenview (" + hashCode() + ") VISIBLE");
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "greenview (" + hashCode() + ") GONE");
    }

}
