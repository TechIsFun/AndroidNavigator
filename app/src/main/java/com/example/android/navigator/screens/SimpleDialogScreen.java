package com.example.android.navigator.screens;

import android.view.View;
import android.widget.TextView;

import com.example.android.navigator.R;
import com.github.techisfun.android.navigator.Screen;
import com.github.techisfun.android.navigator.ScreenType;

import butterknife.InjectView;

/**
 * Created by amaglie on 11/4/15.
 */
public class SimpleDialogScreen extends Screen {

    private static final String TAG = SimpleDialogScreen.class.getSimpleName();

    @InjectView(R.id.text1)
    protected TextView mTextView;

    @Override
    public void afterViewInjection(View view) {

        mTextView.setText("This is a dialog!");
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_screen_layout;
    }

    @Override
    public String getId() {
        return TAG;
    }

    @Override
    public ScreenType getType() {
        return ScreenType.DIALOG;
    }
}
