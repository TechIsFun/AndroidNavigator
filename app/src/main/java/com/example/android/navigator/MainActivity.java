package com.example.android.navigator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.navigator.screens.FirstScreen;
import com.github.techisfun.android.navigator.Navigator;


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
