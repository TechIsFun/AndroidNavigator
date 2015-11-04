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
public class FirstScreen extends Screen {

    private static final String TAG = FirstScreen.class.getSimpleName();

    /*
     * View injection with ButterKnife.
     * Injected views will be automatically removed and re-injected on configuration changes
     */
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
        // the layout resource for this screen
        return R.layout.first_screen_layout;
    }

    @Override
    public String getId() {
        // return and identifier for this screen
        return TAG;
    }

    @OnClick(R.id.btn_go_to_second_screen)
    public void onButtonClicked(View v) {
        // go to another screen
        getNavigator().goTo(new SecondScreen());
    }
}
