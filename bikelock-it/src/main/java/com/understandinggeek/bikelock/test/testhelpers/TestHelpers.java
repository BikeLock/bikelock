package com.understandinggeek.bikelock.test.testhelpers;

import android.widget.ProgressBar;
import com.jayway.android.robotium.solo.Solo;

public class TestHelpers {
    private Solo solo;

    public TestHelpers(Solo solo) {
        this.solo = solo;
    }

    public ProgressBar getProgressBar(int id) {
        return (ProgressBar) solo.getView(id);
    }

    public void assertCurrentActivityIs(String activityName) {
        solo.assertCurrentActivity("Current activity is not "+activityName, activityName);
    }
}
