package com.joeracosta.sampleapp.view.StackScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.joeracosta.sampleapp.R;

import com.joeracosta.library.Screen;
import com.joeracosta.library.Stack.ViewStackHost;
import com.joeracosta.sampleapp.animation.CircularHide;

import com.joeracosta.library.ViewFactory;
import com.joeracosta.library.Stack.ViewStack;

public class BlueScreen extends Screen {

    public static class Factory extends ViewFactory {
        @Override
        public int getLayoutResource() {
            return R.layout.blue_screen;
        }
    }

    public BlueScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "BlueView (" + hashCode() + ") created");
    }



    @Override
    protected void onScreenAttached() {
        Log.d("testing", "BlueView (" + hashCode() + ") onAttachedToWindow");
        super.onScreenAttached();

        final ViewStack viewStack = ((ViewStackHost) getParent()).getViewStack();

        findViewById(R.id.blue_button_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing", "BlueView popping itself");
                viewStack.popWithAnimation(new CircularHide());
            }
        });
    }

    @Override
    public int getViewId() {
        return R.id.blue_screen;
    }

    @Override
    protected void onScreenDetached() {
        Log.d("testing", "BlueView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetached();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "BlueView (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "BlueView (" + hashCode() + ") onRestoreInstanceState");
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "BlueView (" + hashCode() + ") VISIBLE");
        Bundle data = getPassedData();
        if (data != null){
            String toast = data.getString(GreenScreen.TEST_GREEN_KEY);

            if (toast != null){

                Toast.makeText(getContext(), "Text Passed: " + toast,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "BlueView (" + hashCode() + ") GONE");
    }
}
