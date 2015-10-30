package com.andreamaglie.android.navigation.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andreamaglie.android.navigation.Navigator;
import com.andreamaglie.android.navigation.R;


public class MainActivity extends AppCompatActivity {

    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigator = Navigator.getInstanceFor(this);
        mNavigator.setContainerResId(R.id.container);
        mNavigator.setActionBar(getSupportActionBar());

        mNavigator.goTo(new FirstScreen());
    }

    @Override
    public void onBackPressed() {
        if(!mNavigator.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
