PancakesOnPlates
----

PancakesOnPlates is a View-based navigation library for Android. It is forked from Matt Logan's Pancakes library, located [here](https://github.com/mattlogan/Pancakes). PancakesOnPlates aims to expand upon Pancakes and make it more versatile. In addition to Stack-based navigation, the library also allows for Map-based navigation.

Download
----
Add JitPack repo in your root build.gradle at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Add the dependency:
```java
dependencies {
	        compile 'com.github.JayyyR:PancakesOnPlates:v0.2-beta'
	}
```

Usage
-----

Activities, Fragments, and Views can all be StackHosts or MapHosts. The advantage to using this library is you don't need Fragments, and you don't need more than Activity.

In your host class, implement `ViewStackHost`, and create a `ViewStack` instance with a `ViewGroup` container and a `ViewStackDelegate`:

```java
ViewStack viewStack = ViewStack.create(container, this);
```

Or implement `ViewMapHost`, and create a `ViewMap` instance with a `ViewGroup` container:

```java
ViewMap viewMap = ViewMap.create(container, this);
```


Create a `ViewFactory` for each `View` that you want in your stack or map. The ViewFactory should indicate the layout of the View:

```java
public class RedViewFactory implements ViewFactory {
     @Override
        public int getLayoutResource() {
            return R.layout.red_screen;
        }
}
```

For a stack, add a `View` to your container by pushing a `ViewFactory`:

```java
viewStack.push(new RedViewFactory());
```

For a map, show a `View` in your container by showing a `ViewFactory` with the unique id of the view:

```java
viewMap.show(R.id.red_view, new RedViewFactory());
```

In your `onBackPressed()` method, call 

```java
viewStack.onBackPressed()
```
or
```java
viewMap.onBackPressed()
```

These methods will return true if the back press was handled, false if not. These methods will also call the `onBackPressed()` method of any View in the map or stack that implements `BackPressListener`. If there are none, the `ViewStack` will pop the top most view.

For a `ViewStack`, you can also use an `AnimatorFactory` along with `pushWithAnimation(ViewFactory, AnimatorFactory)` and `popWithAnimation(AnimatorFactory)` to add remove a `View` with a transition animation.

```java
public class CircularReveal implements AnimatorFactory {
    @Override
    public Animator createAnimator(View view) {
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        return ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
    }
}
```

You can also call `peek()` and `peekView()` to get the `ViewFactory` and `View` at the top of the navigation stack.

If you want to be notified of changes in the stack or map, add a `StackChangedListener` or `ViewMapSwappedListener`:

```java
viewStack.addStackChangedListener(listener);
```
```java
viewMap.addViewMapSwappedListener(listener);
```

You can also remove individual listeners with `removeStackChangedListener(StackChangedListener)` / `removeViewMapSwappedListener(ViewMapSwappedListener)` or remove all of them with `clearStackChangedListeners()` / `clearViewMapSwappedListeners`.

Persist `ViewFactory` instances, in order, across configuration changes:

```java
@Override
public void onSaveInstanceState(Bundle outState) {
    viewStack.saveToBundle(outState, TAG);
    super.onSaveInstanceState(outState);
}
```
```java
@Override
public void onSaveInstanceState(Bundle outState) {
    viewMap.saveToBundle(outState, TAG);
    super.onSaveInstanceState(outState);
}
```

Rebuild the stack from a `Bundle`:
```java
if (savedInstanceState != null) {
    viewStack.rebuildFromBundle(savedInstanceState, TAG);
}
```
```java
if (savedInstanceState != null) {
    viewMap.rebuildFromBundle(savedInstanceState, TAG);
}
```

Finally, implement `ViewStackDelegate.finishStack()` to take appropriate action when the last view is manually popped. i.e. a call to `viewStack.pop()` with one view left. `finishStack()` will not be called on a back press with one view left in the stack:
```java
@Override
public void finishStack() {
    finish();
}
```

See the [sample app](https://github.com/JayyyR/PancakesOnPlates/tree/master/sampleapp) for an example implementation.

**Be careful: because `ViewFactory` instances are persisted across configuration changes,
you should not keep references in a `ViewFactory` to any objects that should be garbage collected
on a configuration change. Keep each `ViewFactory` as simple as possible.**

Screens
----

The `Screen` class is an abstract class that adds convenience methods to a ViewGroup. A `Screen` should be thought of as one Screen on the device. It can contain one view, or it can contain many custom views. The implementation of this class should contain a ViewFactory so it can be properly added to a ViewStack or ViewMap.

A `Screen` can also be thought of as a Fragment replacement. It has much of the same functionality you're used to but without all the overhead. Here are some of the methods it provides:

```java
    /**
     * Called when a screen is attached to the window.
     */
    protected void onScreenAttached()

    /**
     * Called when a Screen is detached from the window. This won't necessarily be called
     * just because a Screen is no longer visible to the user.
     */
    protected void onScreenDetached()
    
      /**
     * Called when screen is destroyed and is saving state
     * @param bundle the bundle to return. Add what you want to this before returning it back.
     * @return
     */
    protected Bundle onSaveState(Bundle bundle)

    /**
     * Called when screen is restored with data
     * @param bundle The bundle with saved stuff. Grab your stuff from this bundle.
     */
    protected void onRestoreState(Bundle bundle)
    
     /**
     * Called when screen becomes visible on screen. The screen might have been created or brought
     * back into view
     */
    protected void onScreenVisible()

    /**
     * Called when screen is gone from view or detached. The screen still might exist and be attached, but
     * it is not visible
     */
    protected void onScreenGone()
```

You can easily add Screens to your ViewMap or ViewStack. Just extend `Screen` and you'll be good to go. You can even pass data between Screens easily. Just create a bundle and call `passData()` on your ViewFactory:

```java
BlueScreen.Factory blueFactory = new BlueScreen.Factory();
Bundle data = new Bundle();
data.putString(TEXT_KEY, stringToPass);
blueFactory.passData(data);
viewStack.push(blueFactory);
```

Then in the Screen your pushing to call `getPassedData()` to get the bundle you passed:

```java
Bundle data = getPassedData();
if (data != null){
    String text = data.getString(TEXT_KEY);
}
```

License
-----

```
The MIT License (MIT)

Copyright (c) 2015 Joseph Acosta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
