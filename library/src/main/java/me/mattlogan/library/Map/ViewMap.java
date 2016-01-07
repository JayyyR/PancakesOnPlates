package me.mattlogan.library.Map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.mattlogan.library.AnimatorFactory;
import me.mattlogan.library.BackPressListener;
import me.mattlogan.library.FirstLayoutListener;
import me.mattlogan.library.Screen;
import me.mattlogan.library.ViewFactory;

import static me.mattlogan.library.Preconditions.checkNotNull;
import static me.mattlogan.library.Preconditions.checkStringNotEmpty;

/**
 * Created by Joe on 1/4/2016.
 * This is a class used as a Map for Views. Hosts can use this class to contain many views that
 * don't have a hierarchy.
 */
public final class ViewMap {

    private static final String SHOWING_VIEW_TAG = "com.jracosta.showingView";

    private final HashMap<Integer, ViewFactory> map = new HashMap<>();
    private final ViewGroup container;
    private final List<ViewMapSwappedListener> listeners = new ArrayList<>();
    private int showingView;

    /**
     * Static creation method for ViewMap instances
     *
     * @param container Any ViewGroup container for navigation Views. Typically a FrameLayout
     * @return A new ViewMap instance
     */
    public static ViewMap create(ViewGroup container) {
        checkNotNull(container, "container == null");
        return new ViewMap(container);
    }

    private ViewMap(ViewGroup container) {
        this.container = container;
    }

    /**
     * Saves the ViewMap state (an ordered stack of ViewFactories) to the provided Bundle using
     * the provided tag
     *
     * @param bundle The Bundle in which to save the serialized Stack of ViewFactories
     * @param tag    The tag, or "bundle key," for the stored data
     */
    public void saveToBundle(Bundle bundle, String tag) {

        checkNotNull(bundle, "bundle == null");
        checkStringNotEmpty(tag, "tag is empty");
        bundle.putSerializable(tag, map);
        bundle.putInt(SHOWING_VIEW_TAG, showingView);
    }

    /**
     * Resets the navigation stack state to what it was when saveToBundle() was called.
     *
     * @param bundle A bundle containing saved ViewMap state
     * @param tag    The tag, or key, for which the ViewMap state was saved
     */
    @SuppressWarnings("unchecked")
    public void rebuildFromBundle(Bundle bundle, String tag) {
        checkNotNull(bundle, "bundle == null");
        checkStringNotEmpty(tag, "tag is empty");
        HashMap<Integer, ViewFactory> savedMap = (HashMap<Integer, ViewFactory>) bundle.getSerializable(tag);
        checkNotNull(savedMap, "Bundle doesn't contain any ViewMap state.");
        for (HashMap.Entry<Integer, ViewFactory> entry : savedMap.entrySet()) {
            int key = entry.getKey();
            ViewFactory viewFactory = entry.getValue();
            checkNotNull(viewFactory, "viewFactory == null");
            showWithoutNotifyingListeners(key, viewFactory);
        }

        //bring last showing view to top
        showingView = bundle.getInt(SHOWING_VIEW_TAG);
        if (map.get(showingView) !=  null) {
            showWithoutNotifyingListeners(showingView, map.get(showingView));
        }
        notifyListeners();
    }

    /**
     * Shows a View, created by the provided ViewFactory. If the view doesn't exist yet in the map, it will add it.
     *
     * @param key the id of the view, must be unique
     * @param viewFactory responsible for the creation of the next View in the navigation stack
     * @return the provided ViewFactory (to comply with the Java Stack API)
     */
    public ViewFactory show(int key, ViewFactory viewFactory) {
        checkNotNull(viewFactory, "viewFactory == null");
        showWithoutNotifyingListeners(key, viewFactory);
        notifyListeners();
        return viewFactory;
    }

    private void showWithoutNotifyingListeners(int key, ViewFactory viewFactory) {

        showingView = key;

        if (!map.containsKey(key)){
            map.put(key, viewFactory);
            View view = viewFactory.createView(container.getContext(), container);
            container.addView(view);
        }
        else{
            View viewToFront = container.findViewById(key);

            //pass any bundle to the screen if it was set. This is called here since we're not
            //recreating the view in this instance but may still want to pass data if it was set
            if (viewToFront instanceof Screen && viewFactory.getDataToPass() != null) {
                ((Screen) viewToFront).setPassedData(viewFactory.getDataToPass());
            }
            viewFactory.deleteDataToPass(); //reset data in ViewFactory to null

            container.bringChildToFront(viewToFront);
        }
        setAppropriateVisibility();
    }

    /**
     * Shows a View, created by the provided ViewFactory, animates
     * it using the Animator created by the provided AnimatorFactory
     *
     * @param viewFactory     responsible for the creation of the next View in the navigation stack
     * @param animatorFactory responsible for the creation of an Animator to animate the next View
     *                        onto the navigation stack
     * @return the provided ViewFactory (to comply with the Java Stack API)
     */
    public ViewFactory pushWithAnimation(int key, ViewFactory viewFactory,
                                         final AnimatorFactory animatorFactory) {
        checkNotNull(viewFactory, "viewFactory == null");
        checkNotNull(animatorFactory, "animatorFactory == null");
        View view;
        if (!map.containsKey(key)){
            map.put(key, viewFactory);
            view = viewFactory.createView(container.getContext(), container);
            container.addView(view);
        }
        else{
            view = container.findViewById(key);
            container.bringChildToFront(view);
        }
        notifyListeners();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new FirstLayoutListener(view) {
            @Override
            public void onFirstLayout(View view) {
                // We have to wait until the View's first layout pass to start the animation,
                // otherwise the view's width and height would be zero.
                startAnimation(animatorFactory, view, swapAnimatorListener);
            }
        });
        return viewFactory;
    }

    /**
     * Clears the map and removes all Views from the provided ViewGroup container
     */
    public void clear() {
        map.clear();
        container.removeAllViews();
        notifyListeners();
    }

    /**
     * Adds a ViewMapSwappedListener for map-changed events
     *
     * @param listener A ViewMapSwappedListener
     * @return always true
     */
    public boolean addViewMapSwappedListener(ViewMapSwappedListener listener) {
        return listeners.add(listener);
    }

    /**
     * Removes the supplied ViewMapSwappedListener
     *
     * @param listener The ViewMapSwappedListener to remove
     * @return true if the ViewMapSwappedListener was actually removed
     */
    public boolean removeViewMapSwappedListener(ViewMapSwappedListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Removes all ViewMapSwappedListeners
     */
    public void clearViewMapSwappedListeners() {
        listeners.clear();
    }

    private Animator.AnimatorListener swapAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animator) {
            setAppropriateVisibility();
        }
    };

    /**
     * Show the view you want, hide the view that was showing before
     */
    private void setAppropriateVisibility() {
        if (container.getChildCount() > 1) {
            container.getChildAt(container.getChildCount()-2).setVisibility(View.GONE);
            container.getChildAt(container.getChildCount()-1).setVisibility(View.VISIBLE);
        }

    }

    private void startAnimation(AnimatorFactory animatorFactory, View view,
                                Animator.AnimatorListener listener) {
        Animator animator = animatorFactory.createAnimator(view);
        animator.addListener(listener);
        animator.start();
    }

    private void notifyListeners() {
        for (ViewMapSwappedListener listener : listeners) {
            listener.onViewSwapped();
        }
    }

    /**
     * Call when back is pressed to determine if showing view is handling a back press
     * @return true if back is handled, false if not
     */
    public boolean onBackPressed(){
        if (map.get(showingView) != null &&
                container.findViewById(showingView) instanceof BackPressListener){
            return ((BackPressListener) container.findViewById(showingView)).onBackPressed();
        }
        return false;
    }

    public int getShowingViewID(){
        return showingView;
    }

}
