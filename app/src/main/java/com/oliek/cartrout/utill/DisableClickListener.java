package com.oliek.cartrout.utill;

import android.os.SystemClock;
import android.view.View;

public abstract class DisableClickListener implements View.OnClickListener {

    protected int defaultInterval;
    private long lastTimeClicked = 0;

    public DisableClickListener() {
        this(1000);
    }

    public DisableClickListener(int minInterval) {
        this.defaultInterval = minInterval;
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return;
        }
        lastTimeClicked = SystemClock.elapsedRealtime();
        v.setEnabled(true);

        performClick(v);
        disableClick(v);
    }

    public abstract void performClick(View v);
    public abstract void disableClick(View v);

}
