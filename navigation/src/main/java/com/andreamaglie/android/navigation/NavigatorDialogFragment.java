package com.andreamaglie.android.navigation;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;


public class NavigatorDialogFragment extends DialogFragment {

    private static final String TAG = NavigatorDialogFragment.class.getSimpleName();

    private Screen mScreen;
    private Navigator mNavigator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mNavigator = Navigator.getInstanceFor(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mNavigator == null) {
            throw new IllegalStateException("mNavigator is null!");
        }

        mScreen = mNavigator.getCurrentDialogScreen();

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
    }

}
