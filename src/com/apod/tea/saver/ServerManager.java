package com.apod.tea.saver;

import android.content.Context;

import com.apod.tea.bo.Saver;
import com.apod.tea.http.Web;
import com.apod.tea.http.WebRequest;
import com.apod.tea.http.WebRequest.OnRequestListener;
import com.apod.tea.http.WebResponse;
import com.apod.tea.manager.Config;
import com.apod.tea.util.Share;
import com.apod.tea.util.Utils;

public class ServerManager implements OnRequestListener {
	private final static String TAG = Config.TAG + ".ServerManager";

	private final static int REQUEST_SAVER = 1;// 获取屏保图片

	private static ServerManager mManager = null;
	private static WebResponse mWebResponse = new WebResponse();
	private static WebRequest mRequest;

	private Context mContext;

	private OnDataChangeListener mListener;

	public void setOnDataChangeListener(OnDataChangeListener listener) {
		this.mListener = listener;
	}

	private ServerManager(Context context) {
		mContext = context;
		mRequest = new WebRequest();
		mRequest.setOnRequestListener(this);
	}

	public static ServerManager getInstance(Context context) {
		if (mManager == null) {
			mManager = new ServerManager(context);
		}
		return mManager;
	}

	public WebResponse getWebResponse() {
		return mWebResponse;
	}

	public void execGetSaver() {
		if (Utils.isWifiConnected(mContext)) {
			mRequest.execute(REQUEST_SAVER, true);
		}
	}

	public WebResponse onRequest(int id, boolean show) {
		if (id == REQUEST_SAVER) {
			Saver saver = Web.executeGetSavers(Share.getDeviceCode());
			Share.setSaver(saver);
			mWebResponse.setSaver(saver);
		}
		return mWebResponse;
	}

	public void onResponse(int id, WebResponse response) {
		if (id == REQUEST_SAVER) {
			if (mListener != null) {
				mListener.onGetSaver(response.getSaver());
			}
		}
	}

	public interface OnDataChangeListener {
		public void onGetSaver(Saver saver);
	}
}
