package com.joeracosta.mapapp.view.MapScreens;

import android.app.Activity;
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
import com.joeracosta.mapapp.view.StackScreens.RedScreen;

import me.mattlogan.library.BackPressListener;
import me.mattlogan.library.ViewFactory;
import me.mattlogan.library.Stack.ViewStack;
import me.mattlogan.library.Stack.ViewStackDelegate;

public class StackHostScreen extends Screen implements ViewStackHost,
        ViewStackDelegate, BackPressListener{

    private static final String STACK_TAG = "stack";

    private ViewStack viewStack;

    public static class Factory implements ViewFactory {
        @Override
        public View createView(Context context, ViewGroup container) {
            return LayoutInflater.from(context).inflate(R.layout.stack_host_screen, container, false);
        }
    }

    public StackHostScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "StackHostScreen (" + hashCode() + ") created");
        viewStack = ViewStack.create((ViewGroup) findViewById(getId()), this);
    }


    @Override
    public void finishStack() {
        ((Activity)getContext()).finish();
    }

    @Override
    public ViewStack getViewStack() {
        return viewStack;
    }

    @Override
    protected void onScreenAttached() {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onAttachedToWindow");
        //push new viewfactory if we're not coming from instance state
        if (!isRestored()) {
            viewStack.push(new RedScreen.Factory());
        }
        super.onScreenAttached();
    }

    @Override
    public int getViewId() {
        return R.id.stackhost_screen;
    }

    @Override
    protected void onScreenDetached() {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetached();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onSaveInstanceState");
        viewStack.saveToBundle(bundle, STACK_TAG);
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onRestoreInstanceState");
        viewStack.rebuildFromBundle(bundle, STACK_TAG);
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") VISIBLE");
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") GONE");
    }

    @Override
    public boolean onBackPressed() {
        return viewStack.onBackPressed();
    }
}
