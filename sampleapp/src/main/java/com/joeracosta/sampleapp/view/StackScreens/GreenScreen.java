package com.joeracosta.sampleapp.view.StackScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.joeracosta.sampleapp.R;

import com.joeracosta.library.Screen;
import com.joeracosta.library.Stack.ViewStackHost;
import com.joeracosta.sampleapp.animation.CircularHide;
import com.joeracosta.sampleapp.animation.CircularReveal;

import com.joeracosta.library.ViewFactory;
import com.joeracosta.library.Stack.ViewStack;

public class GreenScreen extends Screen {

    public static String TEST_GREEN_KEY = "testgreenkey";
    private String textEntered;

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

                BlueScreen.Factory blueFactory = new BlueScreen.Factory();

                //pass text to next screen
                String textToPass =((EditText) findViewById(R.id.green_text_entry)).getText().toString();
                if (!textToPass.isEmpty()){
                    Bundle data = new Bundle();
                    data.putString(TEST_GREEN_KEY, textToPass);
                    blueFactory.passData(data);
                }
                viewStack.pushWithAnimation(blueFactory, new CircularReveal());
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
