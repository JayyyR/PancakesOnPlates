package com.joeracosta.mapapp.view.MapScreens;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.joeracosta.mapapp.R;

import com.joeracosta.library.Screen;
import com.joeracosta.library.ViewFactory;

public class WhiteScreen extends Screen {

    public static String TEST_KEY_PASS = "testkeypass";

    public static class Factory extends ViewFactory {

        @Override
        public int getLayoutResource() {
            return R.layout.white_screen;
        }
    }

    public WhiteScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("testing", "WhiteScreen (" + hashCode() + ") created");
    }


    @Override
    protected void onScreenAttached() {
        Log.d("testing", "WhiteView (" + hashCode() + ") onAttachedToWindow");
        super.onScreenAttached();
    }

    @Override
    public int getViewId() {
        return R.id.white_screen;
    }


    @Override
    protected void onScreenDetached() {
        Log.d("testing", "WhiteView (" + hashCode() + ") onDetachedFromWindow");
        super.onScreenDetached();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        Log.d("testing", "WhiteView (" + hashCode() + ") onSaveInstanceState");
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        Log.d("testing", "WhiteView (" + hashCode() + ") onRestoreInstanceState");
        super.onRestoreState(bundle);
    }

    @Override
    protected void onScreenVisible() {
        Log.d("testing", "WhiteView (" + hashCode() + ") VISIBLE");

        Bundle passedData = getPassedData();
        if (passedData != null){
            String toast = passedData.getString(TEST_KEY_PASS);

            if (toast != null){

                Toast.makeText(getContext(), toast,
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onScreenGone() {
        Log.d("testing", "WhiteView (" + hashCode() + ") GONE");
    }
}
