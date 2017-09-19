package com.apod.tea.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apod.tea.manager.Config;

public class BootReceiver extends BroadcastReceiver {
	private final static String TAG = Config.TAG + ".BootReceiver";

	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive action is " + intent.getAction());
		Intent main = new Intent();
		main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		main.setAction("android.apodtea.mainaction");
		context.startActivity(main);
	}

}
