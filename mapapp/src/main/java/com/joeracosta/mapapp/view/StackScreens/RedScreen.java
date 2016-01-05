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
import com.joeracosta.mapapp.animation.CircularReveal;

import me.mattlogan.library.ViewFactory;
import me.mattlogan.library.Stack.ViewStack;

public class RedScreen extends Screen {

    public static class Factory implements ViewFactory {
        @Override
        public View createView(Context context, ViewGroup container) {
            return LayoutInflater.from(context).inflate(R.layout.red_screen, container, false);
        }
    }

    public RedScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "RedView (" + hashCode() + ") created");
    }



    @Override
    protected void onScreenAttached() {
        Log.d("testing", "RedView (" + hashCode() + ") onAttachedToWindow");
        super.onScreenAttached();

        final ViewStack viewStack = ((ViewStackHost) getParent()).getViewStack();

        findViewById(R.id.red_button_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing", "RedView popping itself");
                viewStack.pop();
            }
        });

        findViewById(R.id.red_button_go_to_green).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing", "RedView pushing GreenView");
                viewStack.pushWithAnimation(new GreenScreen.Factory(), new CircularReveal());
            }
        });
    }

    @Override
    protected void onScreenDetatched() {
        Log.d("testing", "RedView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetatched();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "RedView (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "RedView (" + hashCode() + ") onRestoreInstanceState");
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "RedView (" + hashCode() + ") VISIBLE");
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "RedView (" + hashCode() + ") GONE");
    }
}
