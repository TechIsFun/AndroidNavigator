package com.example.android.navigator;

import android.view.View;
import android.widget.TextView;

import com.github.techisfun.android.navigator.Screen;

import butterknife.InjectView;

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

    @Override
    public int getLayoutId() {
        return R.layout.second_screen_layout;
    }

    @Override
    public String getId() {
        return TAG;
    }

}
