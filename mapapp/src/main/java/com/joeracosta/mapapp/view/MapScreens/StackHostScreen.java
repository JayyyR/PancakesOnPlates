package com.joeracosta.mapapp.view.MapScreens;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.joeracosta.mapapp.R;
import me.mattlogan.library.Stack.ViewStackHost;
import com.joeracosta.mapapp.view.StackScreens.RedScreen;

import me.mattlogan.library.BackPressListener;
import me.mattlogan.library.ViewFactory;
import me.mattlogan.library.Stack.ViewStack;
import me.mattlogan.library.Stack.ViewStackDelegate;

public class StackHostScreen extends FrameLayout implements ViewStackHost,
        ViewStackDelegate, BackPressListener{

    private static final String STACK_TAG = "stack";

    private ViewStack viewStack;
    private boolean _restored;


    public static class Factory implements ViewFactory {
        @Override
        public View createView(Context context, ViewGroup container) {
            return LayoutInflater.from(context).inflate(R.layout.stack_host_screen, container, false);
        }
    }

    public StackHostScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "StackHostScreen (" + hashCode() + ") created");
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
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onFinishInflate");

        viewStack = ViewStack.create((ViewGroup) findViewById(R.id.stackhost_screen), this);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onAttachedToWindow");

        //push new viewfactory if we're not coming from instance state
        if (!_restored) {
            viewStack.push(new RedScreen.Factory());
        }
    }

    // Note: This won't be called when we push the next View onto the stack because this View is
    // kept in the container's view hierarchy. It's visibility is just set to gone.
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onDetachedFromWindow");
    }

    // Note: These instance state saving methods will only be called if the view has an id.
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onSaveInstanceState");

        Bundle bundle = new Bundle();
        viewStack.saveToBundle(bundle, STACK_TAG);
        bundle.putParcelable("instanceState", super.onSaveInstanceState());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable savedInstanceState) {
        Log.d("testing", "StackHostScreen (" + hashCode() + ") onRestoreInstanceState");
        if (savedInstanceState instanceof Bundle) {
            Bundle bundle = (Bundle) savedInstanceState;

            viewStack.rebuildFromBundle(bundle, STACK_TAG);

            _restored = true;
            // ... load everything
            savedInstanceState = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        switch (visibility){
            case VISIBLE:
                Log.d("testing", "StackHostScreen (" + hashCode() + ") VISIBLE");
                break;
            case INVISIBLE:
                Log.d("testing", "StackHostScreen (" + hashCode() + ") INVISIBLE");
                break;
            case GONE:
                Log.d("testing", "StackHostScreen (" + hashCode() + ") GONE");
                break;
        }
    }


    @Override
    public boolean onBackPressed() {
        viewStack.pop();
        return true;
    }
}
