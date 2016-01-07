package com.joeracosta.mapapp.view.StackScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.joeracosta.mapapp.R;

import me.mattlogan.library.Screen;
import me.mattlogan.library.Stack.ViewStackHost;

import me.mattlogan.library.ViewFactory;
import me.mattlogan.library.Stack.ViewStack;

public class RedScreen extends Screen {

    public static class Factory extends ViewFactory {

        @Override
        public int getLayoutResource() {
            return R.layout.red_screen;
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
                viewStack.push(new GreenScreen.Factory());
            }
        });
    }

    @Override
    public int getViewId() {
        return R.id.red_screen;
    }

    @Override
    protected void onScreenDetached() {
        Log.d("testing", "RedView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetached();
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
