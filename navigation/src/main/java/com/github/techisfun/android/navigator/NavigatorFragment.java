package com.github.techisfun.android.navigator;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public class NavigatorFragment extends Fragment {

    private static final String TAG = NavigatorFragment.class.getSimpleName();

    private Screen mScreen;
    private Navigator mNavigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mNavigator = Navigator.getInstanceFor(activity);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mNavigator == null) {
            throw new IllegalStateException("mNavigator is null!");
        }

        mScreen = mNavigator.getCurrentScreen();

        if (mScreen == null) {
            throw new IllegalStateException("mScreen is null!");
        }

        View view = inflater.inflate(mScreen.getLayoutId(), container, false);
        ButterKnife.inject(mScreen, view);
        mScreen.afterViewInjection(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScreen != null) {
            mScreen.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onResume();
        if (mScreen != null) {
            mScreen.onPause();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(mScreen);
        mScreen.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mScreen.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mScreen.onOptionsItemSelected(item);
    }

    /*
    @Override
    public Transition getEnterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new Slide();
        } else {
            return null;
        }
    }
    */
}
