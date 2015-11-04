package com.github.techisfun.android.navigator;

import android.app.Fragment;
import android.os.Bundle;

public final class NavigatorDataFragment extends Fragment {

    public static final String TAG = NavigatorDataFragment.class.getSimpleName();

    private Navigator mNavigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Navigator getNavigator() {
        return mNavigator;
    }

    public void setNavigator(Navigator navigator) {
        mNavigator = navigator;
    }
}
