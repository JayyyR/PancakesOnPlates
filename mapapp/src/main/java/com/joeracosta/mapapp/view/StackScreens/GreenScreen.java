package com.joeracosta.mapapp.view.StackScreens;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.joeracosta.mapapp.R;
import com.joeracosta.mapapp.ViewStackHost;
import com.joeracosta.mapapp.animation.CircularHide;
import com.joeracosta.mapapp.animation.CircularReveal;

import me.mattlogan.library.ViewFactory;
import me.mattlogan.library.ViewStack;

public class GreenScreen extends RelativeLayout {

    public static class Factory implements ViewFactory {
        @Override
        public View createView(Context context, ViewGroup container) {
            return LayoutInflater.from(context).inflate(R.layout.screen_green_stack, container, false);
        }
    }

    public GreenScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "GreenView (" + hashCode() + ") created");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("testing", "GreenView (" + hashCode() + ") onFinishInflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("testing", "GreenView (" + hashCode() + ") onAttachedToWindow");

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

    // Note: This won't be called when we push the next View onto the stack because this View is
    // kept in the container's view hierarchy. It's visibility is just set to gone.
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("testing", "GreenView (" + hashCode() + ") onDetachedFromWindow");
    }

    // Note: These instance state saving methods will only be called if the view has an id.
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("testing", "GreenView (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("testing", "GreenView (" + hashCode() + ") onRestoreInstanceState");
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        switch (visibility){
            case VISIBLE:
                Log.d("testing", "GreenView (" + hashCode() + ") VISIBLE");
                break;
            case INVISIBLE:
                Log.d("testing", "GreenView (" + hashCode() + ") INVISIBLE");
                break;
            case GONE:
                Log.d("testing", "GreenView (" + hashCode() + ") GONE");
                break;
        }
    }
}
