package com.joeracosta.library;

import android.view.View;
import android.view.ViewTreeObserver;

public abstract class FirstLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private final View view;
    private boolean first = true;

    protected FirstLayoutListener(View view) {
        this.view = view;
    }

    @Override
    public void onGlobalLayout() {
        if (view.getWidth() > 0 && view.getHeight() > 0 && first) {
            onFirstLayout(view);
            first = false;
        }
    }

    protected abstract void onFirstLayout(View view);
}
