package com.texa.care.navigation;

import android.app.FragmentTransaction;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.andreamaglie.android.app.Navigator;

/**
 * Created by amaglie on 3/20/15.
 */
public abstract class Screen {

    private static final String TAG = Screen.class.getSimpleName();
    private Navigator mNavigator;

    public int getTransaction() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public int getCloseTransaction() {
        return FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
    }

    public abstract void afterViewInjection(View view);

    public abstract int getLayoutId();

    public int getTitle() {
        return 0;
    }

    public abstract String getId();

    /**
     * @return Application context
     */
    protected final Context getContext() {
        return mNavigator.getContext();
    }

    protected final void goTo(Screen screen) {
        mNavigator.goTo(screen);
    }

    protected final void goBack() {
        mNavigator.goBack();
    }

    @Override
    public String toString() {
        return getId();
    }

    /**
     * override me if needed
     */
    public void onResume() {
        // default implementation: do nothing
    }

    /**
     * override me if needed
     */
    public void onPause() {
        // default implementation: do nothing
    }

    /**
     * @return current Navigator instance
     */
    public Navigator getNavigator() {
        return mNavigator;
    }

    public final void setNavigator(Navigator navigator) {
        mNavigator = navigator;
    }

    public ScreenType getType() {
        return ScreenType.DEFAULT;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /**
     * override me if needed
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // default implementation: do nothing
    }

    /**
     * override me if needed
     */
    public boolean onBackPressed() {
        // default implementation: do nothing
        return false;
    }

    public void onDestroyView() {
        // default implementation: do nothing
    }
}
