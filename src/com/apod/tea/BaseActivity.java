package com.apod.tea;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apod.tea.bo.Business;
import com.apod.tea.bo.CheckIn;
import com.apod.tea.bo.Device;
import com.apod.tea.bo.MsgTemplate;
import com.apod.tea.bo.Network;
import com.apod.tea.bo.Saver;
import com.apod.tea.bo.Saver.SaverImg;
import com.apod.tea.http.WebResponse;
import com.apod.tea.manager.ApodManager;
import com.apod.tea.manager.ApodManager.OnDataChangeListener;
import com.apod.tea.manager.Config;
import com.apod.tea.manager.DowloadManager;
import com.apod.tea.manager.DowloadManager.OnDowloadDataChangeListener;
import com.apod.tea.util.Storage;
import com.apod.tea.view.BizView;
import com.apod.tea.view.PduView;

public class BaseActivity extends Activity implements OnDataChangeListener, OnDowloadDataChangeListener {
	private final static String TAG = Config.TAG + ".BaseActivity";
	protected ApodManager mApodManager;
	protected DowloadManager mDowloadManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApodManager = ApodManager.getInstance(this);
		mApodManager.setOnDataChangeListener(this);

		mDowloadManager = DowloadManager.getInstance(this);
		mDowloadManager.setOnDowloadDataChangeListener(this);
	}

	public boolean checkApkExist(Intent intent) {
		try {
			List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
			if (list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	protected void startMyActivity(String action, String path) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (checkApkExist(intent)) {
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		} else {
			Storage.installApk(this, path);
		}
	}

	protected void startMyService(Class cls) {
		Intent intent = new Intent(this, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);
		Log.d(TAG, "startMyService");
	}

	protected void startMyActivity(Class cls) {
		Intent intent = new Intent(this, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public void onGetNetworks(List<Network> list) {

	}

	public void onGetDevices(List<Device> list) {

	}

	public void onCheckIn(CheckIn checkin) {

	}

	public void onGetBusiness(List<Business> business) {

	}

	public void onGetMsgTemplate(WebResponse response, MsgTemplate msgTemplate) {

	}

	public void onDowloadPduImg(PduView pduView) {

	}

	public void onSaveData() {

	}

	public void onReadData(WebResponse response) {

	}

	public void onDowloadBizImg(BizView bizView) {

	}

	public void onGetSaver(Saver saver) {

	}

	public void onDowloadSaverImg(SaverImg saverImg) {

	}

	public void onSendSms(boolean state) {
		Log.d(TAG, "onSendSms state is " + state);
		if (state) {
			Toast.makeText(this, getResources().getString(R.string.hujiao_success), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, getResources().getString(R.string.hujiao_fail), Toast.LENGTH_LONG).show();
		}
	}

	public void onReadSms() {
		
	}
}
