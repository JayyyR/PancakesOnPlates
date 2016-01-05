package com.joeracosta.mapapp.view.MapScreens;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.joeracosta.mapapp.R;

import me.mattlogan.library.ViewFactory;

public class WhiteScreen extends RelativeLayout{

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
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("testing", "WhiteScreen (" + hashCode() + ") onFinishInflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("testing", "WhiteScreen (" + hashCode() + ") onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("testing", "WhiteScreen (" + hashCode() + ") onDetachedFromWindow");
    }

    // Note: These instance state saving methods will only be called if the view has an id.
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("testing", "WhiteScreen (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("testing", "WhiteScreen (" + hashCode() + ") onRestoreInstanceState");
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        switch (visibility){
            case VISIBLE:
                Log.d("testing", "WhiteScreen (" + hashCode() + ") VISIBLE");
                break;
            case INVISIBLE:
                Log.d("testing", "WhiteScreen (" + hashCode() + ") INVISIBLE");
                break;
            case GONE:
                Log.d("testing", "WhiteScreen (" + hashCode() + ") GONE");
                break;
        }
    }
}
