package com.example.android.navigator.screens;

import android.view.View;
import android.widget.TextView;

import com.example.android.navigator.R;
import com.github.techisfun.android.navigator.Screen;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by amaglie on 8/4/15.
 */
public class SecondScreen extends Screen {

    private static final String TAG = SecondScreen.class.getSimpleName();

    @InjectView(R.id.text2)
    protected TextView mTextView2;

    protected String mSampleField = "WOW!";

    @Override
    public void afterViewInjection(View view) {
        // here views have been injected

        mTextView2.setText(mSampleField);
    }

    @OnClick(R.id.button_open_dialog)
    public void openDialog(View view) {
        getNavigator().goTo(new SimpleDialogScreen());
    }

    @Override
    public int getLayoutId() {
        return R.layout.second_screen_layout;
    }

    @Override
    public String getId() {
        return TAG;
    }

}
