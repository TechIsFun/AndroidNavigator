package com.github.techisfun.android.navigator;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DialogTransactionControllerTest {

    @InjectMocks
    DialogTransactionController mDialogTransactionController;

    @Mock
    FragmentManager mFragmentManager;

    @Mock
    Screen mCurrentDialogScreen;

    @Before
    public void setUp() throws Exception {
        mDialogTransactionController = new DialogTransactionController();

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        mDialogTransactionController = null;
    }

    @Test
    public void testDisplay() throws Exception {

    }

    @Test
    public void testGetCurrentDialogScreen() throws Exception {

    }

    @Test
    public void testGoBack_noScreen() throws Exception {
        mCurrentDialogScreen = null;
        assertFalse(mDialogTransactionController.goBack(mFragmentManager));
    }

    @Test
    public void testGoBack_instanceOfFragment() throws Exception {
        when(mCurrentDialogScreen.getId()).thenReturn("id");

        Fragment fragment = mock(Fragment.class);

        when(mFragmentManager.findFragmentByTag("id")).thenReturn(fragment);

        assertFalse(mDialogTransactionController.goBack(mFragmentManager));
    }

    @Test
    public void testGoBack_instanceOfDialogFragment() throws Exception {
        assertNotNull(mDialogTransactionController.getCurrentDialogScreen());

        when(mCurrentDialogScreen.getId()).thenReturn("id");

        DialogFragment fragment = mock(DialogFragment.class);

        when(mFragmentManager.findFragmentByTag("id")).thenReturn(fragment);

        assertTrue(mDialogTransactionController.goBack(mFragmentManager));
        verify(fragment, times(1)).dismiss();
    }
}
