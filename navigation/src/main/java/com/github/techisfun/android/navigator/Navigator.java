package com.github.techisfun.android.navigator;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.util.Log;

import java.util.Stack;

public class Navigator {

    private static final String TAG = Navigator.class.getSimpleName();
    private int mContainerResId;
    private FragmentManager mFragmentManager;
    @Nullable
    private ActionBar mActionBar;

    private Stack<Screen> mScreens = new Stack<>();

    private Application mApplication;
    private DialogTransactionController mDialogTransactionController = new DialogTransactionController();
    private NavigatorFragment mCurrentFragment;

    @VisibleForTesting
    protected Navigator() {
        // do nothing
    }

    public static Navigator getInstanceFor(@NonNull Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();

        NavigatorDataFragment dataFragment = (NavigatorDataFragment) fragmentManager.findFragmentByTag(NavigatorDataFragment.TAG);

        if (dataFragment == null) {
            dataFragment = new NavigatorDataFragment();
            fragmentManager.beginTransaction().add(dataFragment, NavigatorDataFragment.TAG).commit();
        }

        Navigator navigator = dataFragment.getNavigator();

        if (navigator == null) {
            navigator = new Navigator();
            dataFragment.setNavigator(navigator);
        }

        navigator.setFragmentManager(activity.getFragmentManager());
        navigator.setApplication(activity.getApplication());

        return navigator;
    }

    /**
     * http://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
    */
    public void setActionBar(ActionBar actionBar) {
        mActionBar = actionBar;
    }

    public void goTo(Screen screen) {
        goTo(screen, screen.getTransaction());
    }

    public boolean goBack() {
        if (mDialogTransactionController.goBack(mFragmentManager)) {
            return true;
        }

        int closeTransaction;
        Screen current = getCurrentScreen();
        if (current != null) {
            closeTransaction = current.getCloseTransaction();
        } else {
            closeTransaction = FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
        }

        Screen previous = getPreviousScreen();

        if (previous == null) {
            return false;
        }

        doFragmentTransaction(closeTransaction);

        updateTitle(previous);

        return true;

        /*
        Screen parent = null;

        Screen currentScreen = getCurrentScreen();
        if (currentScreen != null) {
            parent = currentScreen.getParent();
        }

        if (parent != null) {
            goTo(parent, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            return true;
        }

        return false;
        */
    }

    private void updateTitle(Screen screen) {
        if (screen == null) {
            Log.d(TAG, "screen is null!");
            return;
        }

        int title = screen.getTitle();
        if (mActionBar != null && title > 0) {
            mActionBar.setTitle(title);
        }
    }

    protected Screen getPreviousScreen() {
        if (mScreens.isEmpty()) {
            return null;
        }

        mScreens.pop();

        if (mScreens.isEmpty()) {
            return null;
        }

        return mScreens.peek();
    }


    private void goTo(Screen screen, int fragmentTransaction) {
        if (isCurrentScreen(screen)) {
            Log.d(TAG, "screen already displayed, doing nothing");
            return;
        }

        screen.setNavigator(this);

        if (screen.getType() == ScreenType.DIALOG) {
            mDialogTransactionController.display(screen, mFragmentManager);
            return;
        }

        trackCurrentScreen(screen);

        doFragmentTransaction(fragmentTransaction);

        updateTitle(screen);

    }

    protected void doFragmentTransaction(int fragmentTransaction) {
        mFragmentManager.beginTransaction()
                .replace(mContainerResId, createNavigatorFragment())
                .setTransition(fragmentTransaction)
                .commit();
    }

    protected Fragment createNavigatorFragment() {
        mCurrentFragment = new NavigatorFragment();
        return mCurrentFragment;
    }

    protected void trackCurrentScreen(Screen screen) {
        mScreens.push(screen);
    }


    public Screen getCurrentScreen() {
        if (mScreens.isEmpty()) {
            return null;
        } else {
            return mScreens.peek();
        }
    }

    private boolean isCurrentScreen(Screen screen) {
        Screen currentScreen = getCurrentScreen();
        if (currentScreen == null) {
            return false;
        }

        if (currentScreen == screen) {
            return true;
        }

        return currentScreen.getId().equals(screen.getId());
    }


    public boolean onBackPressed() {
        boolean result = false;

        Screen screen = getCurrentScreen();
        if (screen != null) {
            result = screen.onBackPressed();
        }

        if (result) {
            return true;
        }

        return goBack();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void setContainerResId(@IdRes int containerResId) {
        mContainerResId = containerResId;
    }

    public Context getContext() {
        return mApplication.getApplicationContext();
    }

    public Application getApplication() {
        return mApplication;
    }

    protected void setApplication(Application application) {
        mApplication = application;
    }

    public void updateTitle() {
        updateTitle(getCurrentScreen());
    }

    public Screen getCurrentDialogScreen() {
        return mDialogTransactionController.getCurrentDialogScreen();
    }

    /**
     * Completely clears the navigation history
     */
    public void clearBackStack() {
        mScreens.clear();
    }

    public NavigatorFragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
