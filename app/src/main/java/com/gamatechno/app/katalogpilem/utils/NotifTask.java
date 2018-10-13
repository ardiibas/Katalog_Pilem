package com.gamatechno.app.katalogpilem.utils;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class NotifTask {
    private GcmNetworkManager mGcmNetworkManager;

    public NotifTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(NotifService.class)
                .setPeriod(3 * 60 * 1000)
                .setFlex(10)
                .setTag(NotifService.TAG_TASK_UPCOMING)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(NotifService.TAG_TASK_UPCOMING, NotifService.class);
        }
    }
}
