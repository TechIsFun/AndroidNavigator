package com.andreamaglie.android.navigation.app;

import android.view.View;
import android.widget.TextView;

import com.andreamaglie.android.navigation.R;
import com.texa.care.navigation.Screen;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by amaglie on 8/4/15.
 */
public class FirstScreen extends Screen {

    private static final String TAG = FirstScreen.class.getSimpleName();

    @InjectView(R.id.text1)
    protected TextView mTextView1;

    @InjectView(R.id.text2)
    protected TextView mTextView2;

    protected int mCounter;

    private String mSampleString = "a sample string";

    @Override
    public void afterViewInjection(View view) {
        // here views have been injected

        mTextView1.setText("afterViewInjection: " + (++mCounter));

        mTextView2.setText(mSampleString);
    }

    @Override
    public int getLayoutId() {
        return R.layout.first_screen_layout;
    }

    @Override
    public String getId() {
        // return and indentifier for this screen
        return TAG;
    }

    @OnClick(R.id.btn_go_to_second_screen)
    public void onButtonClicked(View v) {
        getNavigator().goTo(new SecondScreen());
    }
}
