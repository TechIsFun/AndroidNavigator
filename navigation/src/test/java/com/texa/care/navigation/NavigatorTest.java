package com.texa.care.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.andreamaglie.android.app.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NavigatorTest {

    @Spy
    Stack<Screen> mScreens;

    @Mock
    ActionBar mActionBar;

    @Mock
    FragmentManager mFragmentManager;

    @Mock
    DialogTransactionController mDialogTransactionController;

    @InjectMocks
    Navigator mNavigator;

    @Before
    public void setUp() throws Exception {
        mScreens = new Stack<>();

        mNavigator = new Navigator() {
            @Override
            protected void doFragmentTransaction(int fragmentTransaction) {
                // nothing
            }
        };
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentScreen_emptyStack() throws Exception {
        assertNull(mNavigator.getCurrentScreen());
    }

    @Test
    public void testGetCurrentScreen_notEmptyStack() throws Exception {
        Screen screen1 = mock(Screen.class);
        mScreens.push(screen1);
        assertSame(screen1, mNavigator.getCurrentScreen());
    }

    @Test
    public void testTrackCurrentScreen() {
        Screen screen1 = mock(Screen.class);

        mNavigator.trackCurrentScreen(screen1);

        verify(mScreens, times(1)).push(screen1);
    }

    @Test
    public void testGoBack_emptyStack() {
        assertFalse(mNavigator.goBack());
        verify(mActionBar, never()).setTitle(anyInt());
    }

    @Test
    public void testGoBack_oneItemStack() {
        Screen screen1 = mock(Screen.class);
        mNavigator.trackCurrentScreen(screen1);

        assertFalse(mNavigator.goBack());
        verify(mActionBar, never()).setTitle(anyInt());
    }

    @Test
    public void testGoBack_moreItemsStack() {
        Screen screen1 = mock(Screen.class);
        when(screen1.getTitle()).thenReturn(999);

        Screen screen2 = mock(Screen.class);

        mNavigator.trackCurrentScreen(screen1);
        mNavigator.trackCurrentScreen(screen2);

        assertTrue(mNavigator.goBack());
        verify(mActionBar, times(1)).setTitle(Integer.valueOf(999));
    }

    @Test
    public void testGetPreviousScreen_emptyStack() {
        assertNull(mNavigator.getPreviousScreen());
    }

    @Test
    public void testGetPreviousScreen_oneScreenInStack() {
        Screen screen1 = mock(Screen.class);
        mNavigator.trackCurrentScreen(screen1);

        assertNull(mNavigator.getPreviousScreen());
    }

    @Test
    public void testGetPreviousScreen_moreScreensInStack() {
        Screen screen1 = mock(Screen.class);
        Screen screen2 = mock(Screen.class);

        mNavigator.trackCurrentScreen(screen1);
        mNavigator.trackCurrentScreen(screen2);

        assertSame(screen1, mNavigator.getPreviousScreen());
    }

    @Test
    public void testDisplayDialog() {
        Screen screen1 = mock(Screen.class);
        when(screen1.getType()).thenReturn(ScreenType.DIALOG);

        mNavigator.goTo(screen1);

        verify(mDialogTransactionController, times(1)).display(screen1, mFragmentManager);
    }

    @Test
    public void testBackPressed_handledByScreen() {
        // setup
        Screen screen = mock(Screen.class);
        when(screen.onBackPressed()).thenReturn(true);
        Navigator navigatorSpy = spy(mNavigator);

        navigatorSpy.trackCurrentScreen(screen);
        assertTrue(navigatorSpy.onBackPressed());

        verify(screen, times(1)).onBackPressed();
        verify(navigatorSpy, never()).goBack();
    }

    @Test
    public void testBackPressed_notHandledByScreen() {
        // setup
        Screen screen = mock(Screen.class);
        when(screen.onBackPressed()).thenReturn(false);
        Navigator navigatorSpy = spy(mNavigator);

        navigatorSpy.trackCurrentScreen(screen);
        assertFalse(navigatorSpy.onBackPressed());

        verify(screen, times(1)).onBackPressed();
        verify(navigatorSpy, times(1)).goBack();
    }

    @Test
    public void testGetInstanceFor_dataFragmentNull() {
        Activity activity = mockActivityWithFragmentManager();
        assertNotNull(Navigator.getInstanceFor(activity));
    }

    @Test
    public void testGetInstanceFor_dataFragmentWithNoData() {
        Activity activity = mockActivityWithFragmentManager();

        NavigatorDataFragment dataFragment = new NavigatorDataFragment();
        when(mFragmentManager.findFragmentByTag(NavigatorDataFragment.TAG)).thenReturn(dataFragment);

        assertNotNull(Navigator.getInstanceFor(activity));
    }

    @Test
    public void testGetInstanceFor() {
        Activity activity = mockActivityWithFragmentManager();

        NavigatorDataFragment dataFragment = new NavigatorDataFragment();

        Navigator instance = new Navigator();
        dataFragment.setNavigator(instance);

        when(mFragmentManager.findFragmentByTag(NavigatorDataFragment.TAG)).thenReturn(dataFragment);

        assertEquals(instance, Navigator.getInstanceFor(activity));
    }

    private Activity mockActivityWithFragmentManager() {
        Activity activity = mock(Activity.class);
        when(activity.getFragmentManager()).thenReturn(mFragmentManager);

        FragmentTransaction fragmentTransaction = mock(FragmentTransaction.class);

        when(mFragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        when(fragmentTransaction.add(any(Fragment.class), anyString())).thenReturn(fragmentTransaction);

        return activity;
    }
}
