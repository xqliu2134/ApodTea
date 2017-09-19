package com.apod.tea.manager;

import android.content.Context;

import com.apod.tea.bo.Saver.SaverImg;
import com.apod.tea.http.DowloadRequest;
import com.apod.tea.http.DowloadRequest.OnDowloadRequestListener;
import com.apod.tea.http.Web;
import com.apod.tea.view.BizView;
import com.apod.tea.view.PduView;

public class DowloadManager implements OnDowloadRequestListener {
	private final static String TAG = Config.TAG + ".DowloadManager";

	private final static int REQUEST_DOWLOAD_PDUIMG = 1;// 下载产品图标
	private final static int REQUEST_DOWLOAD_BIZIMG = 2;// 下载模块图标
	private final static int REQUEST_DOWLOAD_SAVEIMG = 3;// 下载屏保图片

	private static DowloadManager mManager = null;
	private static DowloadRequest mRequest;

	private Context mContext;

	private OnDowloadDataChangeListener mListener;

	public void setOnDowloadDataChangeListener(OnDowloadDataChangeListener listener) {
		this.mListener = listener;
	}

	private DowloadManager(Context context) {
		mContext = context;
		mRequest = new DowloadRequest();
		mRequest.setOnRequestListener(this);
	}

	public static DowloadManager getInstance(Context context) {
		if (mManager == null) {
			mManager = new DowloadManager(context);
		}
		return mManager;
	}

	public void execDowloadPduImg(PduView pduView) {
		mRequest.execute(REQUEST_DOWLOAD_PDUIMG, pduView);
	}

	public void execDowloadBizImg(BizView bizView) {
		mRequest.execute(REQUEST_DOWLOAD_BIZIMG, bizView);
	}
	
	public void execDowloadSaverImg(SaverImg saverImg) {
		mRequest.execute(REQUEST_DOWLOAD_SAVEIMG, saverImg);
	}

	public Object onRequest(int id, Object obj) {
		if (REQUEST_DOWLOAD_PDUIMG == id) {
			return Web.executeDowloadPduImg(obj);
		} else if (id == REQUEST_DOWLOAD_BIZIMG) {
			return Web.executeDowloadBizImg(obj);
		}else if (id == REQUEST_DOWLOAD_SAVEIMG) {
			return Web.executeDowloadSaverImg(obj);
		}

		return null;
	}

	public void onResponse(int id, Object obj) {
		if (REQUEST_DOWLOAD_PDUIMG == id) {
			if (mListener != null) {
				if (obj != null && (obj instanceof PduView)) {
					mListener.onDowloadPduImg((PduView) obj);
				}
			}
		} else if (id == REQUEST_DOWLOAD_BIZIMG) {
			if (mListener != null) {
				if (obj != null && (obj instanceof BizView)) {
					mListener.onDowloadBizImg((BizView) obj);
				}
			}
		}else if (id == REQUEST_DOWLOAD_SAVEIMG) {
			if (mListener != null) {
				if (obj != null && (obj instanceof SaverImg)) {
					mListener.onDowloadSaverImg((SaverImg) obj);
				} else {
					mListener.onDowloadSaverImg(null);
				}
			}
		}
	}

	public interface OnDowloadDataChangeListener {
		public void onDowloadPduImg(PduView pduView);

		public void onDowloadBizImg(BizView bizView);
		
		public void onDowloadSaverImg(SaverImg saverImg);
	}
}
