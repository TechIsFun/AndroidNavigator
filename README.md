
[![Build Status](https://travis-ci.org/TechIsFun/AndroidNavigation.svg?branch=master)](https://travis-ci.org/TechIsFun/AndroidNavigation)

# AndroidNavigation

Use POJOs instead of Activities and/or Fragments to handle the different _screens_ of an app!

* Pojo will survive across configuration changes.
* Useful for fast app prototyping.
* AndroidNavigation users [ButterKnife](https://github.com/JakeWharton/butterknife) for injecting views.

## 2 Steps Usage

Create an instance of Screen:

```java
public class FirstScreen extends Screen {

    private static final String TAG = FirstScreen.class.getSimpleName();

    /*
     * View injection with ButterKnife.
     * Injected views will be automatically removed and re-injected on configuration changes
     */
    @InjectView(R.id.text1) protected TextView mTextView1;
    @InjectView(R.id.text2) protected TextView mTextView2;

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
```


Create an Activity with an instance of Navigator:

```java
public class MainActivity extends AppCompatActivity {

    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigator = Navigator.getInstanceFor(this);
        mNavigator.setContainerResId(R.id.container); // id of FrameLayout that will act as fragment container
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
```
