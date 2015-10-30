package com.andreamaglie.android.navigation;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;

public class DialogTransactionController {

    private Screen mCurrentDialogScreen;

    public void display(@NonNull Screen screen, @NonNull FragmentManager fm) {
        mCurrentDialogScreen = screen;
        NavigatorDialogFragment navigatorDialogFragment = new NavigatorDialogFragment();
        navigatorDialogFragment.show(fm, screen.getId());
    }

    public Screen getCurrentDialogScreen() {
        return mCurrentDialogScreen;
    }

    public boolean goBack(@NonNull FragmentManager fm) {
        if (mCurrentDialogScreen == null) {
            return false;
        }

        Fragment fragment = fm.findFragmentByTag(mCurrentDialogScreen.getId());
        if (fragment == null) {
            return false;
        }

        if (fragment instanceof DialogFragment) {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
            mCurrentDialogScreen = null;
            return true;
        }

        return false;
    }
}
