package com.example.b3tempocasalini;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TempoAlarmReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = TempoAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive()");
    }
}
