package com.apod.tea.manager;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.apod.tea.R;
import com.apod.tea.bo.Business;
import com.apod.tea.bo.CheckIn;
import com.apod.tea.bo.Device;
import com.apod.tea.bo.MsgTemplate;
import com.apod.tea.bo.Network;
import com.apod.tea.http.Web;
import com.apod.tea.http.WebRequest;
import com.apod.tea.http.WebRequest.OnRequestListener;
import com.apod.tea.http.WebResponse;
import com.apod.tea.util.Storage;
import com.apod.tea.util.Utils;
import com.apod.tea.util.XmlUtils;

public class ApodManager implements OnRequestListener {
	private final static String TAG = Config.TAG + ".ApodManager";

	private final static int REQUEST_NETWORK = 1;// 获取网络网点
	private final static int REQUEST_DEVICE = 2;// 获取网络设备类型
	private final static int REQUEST_CHECKIN = 3;// checkin
	private final static int REQUEST_INITDIR = 5;// 初始化文件路径
	private final static int REQUEST_BUSINESS = 6;// 获取业务模块信息
	private final static int REQUEST_SAVEDATA = 7;// 保存数据
	private final static int REQUEST_READDATA = 8;// 读取数据
	private final static int REQUEST_HEARTBEAT = 9;// 心跳机制
	private final static int REQUEST_SENDSMS = 10;// 发送SMS
	private final static int REQUEST_READSMS = 11;// 读取SMS

	private static ApodManager mManager = null;
	private static WebResponse mWebResponse = new WebResponse();
	private static WebRequest mRequest;

	private Context mContext;

	private OnDataChangeListener mListener;

	public void setOnDataChangeListener(OnDataChangeListener listener) {
		this.mListener = listener;
	}

	private ApodManager(Context context) {
		mContext = context;
		mRequest = new WebRequest();
		mRequest.setOnRequestListener(this);
	}

	public static ApodManager getInstance(Context context) {
		if (mManager == null) {
			mManager = new ApodManager(context);
		}
		return mManager;
	}

	public WebResponse getWebResponse() {
		return mWebResponse;
	}

	public void initDefaultDirectory() {
		mRequest.execute(REQUEST_INITDIR, true);
	}

	public void checkIn() {
		mRequest.execute(REQUEST_CHECKIN, true);
	}

	public void execGetNetworks() {
		mRequest.execute(REQUEST_NETWORK, true);
	}

	public void execGetDevice() {
		mRequest.execute(REQUEST_DEVICE, true);
	}

	public void execGetBusiness() {
		if (Utils.isWifiConnected(mContext)) {
			mRequest.execute(REQUEST_BUSINESS, true);
		}
	}

	public void execSaveData() {
		if (Utils.isWifiConnected(mContext)) {
			mRequest.execute(REQUEST_SAVEDATA, true);
		}
	}

	public void execReadData() {
		mRequest.execute(REQUEST_READDATA, true);
	}

	public void execHeartbeat() {
		if (Utils.isWifiConnected(mContext)) {
			mRequest.execute(REQUEST_HEARTBEAT, true);
		}
	}

	public void execReadSms() {
		mRequest.execute(REQUEST_READSMS, true);
	}

	public void execSendSms() {
		if (Utils.isWifiConnected(mContext)) {
			mRequest.execute(REQUEST_SENDSMS, true);
		} else {
			Toast.makeText(mContext, mContext.getResources().getString(R.string.send_sms_msg), Toast.LENGTH_LONG).show();
		}
	}

	public WebResponse onRequest(int id, boolean show) {
		if (id == REQUEST_NETWORK) {
			mWebResponse.setNetworks(Web.executeGetNetworks());
		} else if (id == REQUEST_DEVICE) {
			mWebResponse.setDevices(Web.executeGetDeviceTypes());
		} else if (id == REQUEST_CHECKIN) {
			if (Utils.isWifiConnected(mContext)) {
				mWebResponse.setCheckin(Web.executeCheckInDevice(Utils.getLocalMacAddress(mContext)));
			}
		} else if (id == REQUEST_INITDIR) {
			Storage.initDefaultDirectory(mContext);
		} else if (id == REQUEST_BUSINESS) {
			mWebResponse.setBusiness(Web.executeGetBusiness(mWebResponse.getCheckin().getDeviceCode()));
		} else if (id == REQUEST_SAVEDATA) {
			XmlUtils.save(Storage.getDataFile(), mWebResponse);
		} else if (id == REQUEST_READDATA) {
			if (!Utils.isWifiConnected(mContext)) {
				mWebResponse = XmlUtils.read(Storage.getDataFile(), mWebResponse);
			}
		} else if (id == REQUEST_HEARTBEAT) {
			Web.executeHeartbeat(mWebResponse.getCheckin().getDeviceCode());
		} else if (id == REQUEST_SENDSMS) {
			mWebResponse.setSmsFlag(Web.executeSendSms(mWebResponse.getCheckin().getDeviceCode()));
		} else if (id == REQUEST_READSMS) {
			XmlUtils.readSms(Storage.getDataFile());
		}
		return mWebResponse;
	}

	public void onResponse(int id, WebResponse response) {
		if (id == REQUEST_NETWORK) {
			if (mListener != null) {
				mListener.onGetNetworks(response.getNetworks());
			}
		} else if (id == REQUEST_DEVICE) {
			if (mListener != null) {
				mListener.onGetDevices(response.getDevices());
			}
		} else if (id == REQUEST_CHECKIN) {
			if (mListener != null) {
				mListener.onCheckIn(response.getCheckin());
			}
		} else if (id == REQUEST_BUSINESS) {
			if (mListener != null) {
				mListener.onGetBusiness(response.getBusiness());
			}
		} else if (id == REQUEST_SAVEDATA) {
			if (mListener != null) {
				mListener.onSaveData();
			}
		} else if (id == REQUEST_READDATA) {
			if (mListener != null) {
				mListener.onReadData(mWebResponse);
			}
		} else if (id == REQUEST_SENDSMS) {
			if (mListener != null) {
				mListener.onSendSms(response.isSmsFlag());
			}
		} else if (id == REQUEST_READSMS) {
			if (mListener != null) {
				mListener.onReadSms();
			}
		}
	}

	public interface OnDataChangeListener {
		public void onGetNetworks(List<Network> list);

		public void onGetDevices(List<Device> list);

		public void onCheckIn(CheckIn checkin);

		public void onSaveData();

		public void onReadData(WebResponse response);

		public void onGetMsgTemplate(WebResponse response, MsgTemplate msgTemplate);

		public void onGetBusiness(List<Business> business);

		public void onSendSms(boolean state);

		public void onReadSms();
	}
}
