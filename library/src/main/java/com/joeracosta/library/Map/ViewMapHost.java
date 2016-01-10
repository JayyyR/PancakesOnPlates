package com.joeracosta.library.Map;

/**
 * Created by Joe on 1/4/2016.
 * Have the View/Activity/Fragment that you want to host your ViewMap implement this
 */
public interface ViewMapHost {

    /**
     * @return the Host's ViewMap
     */
    ViewMap getViewMap();
}
