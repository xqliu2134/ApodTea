package com.apod.tea.http;

import java.util.List;

import com.apod.tea.bo.Business;
import com.apod.tea.bo.CheckIn;
import com.apod.tea.bo.Device;
import com.apod.tea.bo.MsgTemplate;
import com.apod.tea.bo.Network;
import com.apod.tea.bo.Saver;
import com.apod.tea.bo.Sms;

public class WebResponse {
	private int requestType;
	private List<Network> networks;
	private List<Device> devices;
	private CheckIn checkin;
	private List<Business> business;
	private MsgTemplate msgTemplate;
	private Saver saver;
	private boolean smsFlag;

	public boolean isSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(boolean smsFlag) {
		this.smsFlag = smsFlag;
	}

	public Saver getSaver() {
		return saver;
	}

	public void setSaver(Saver saver) {
		this.saver = saver;
	}

	public MsgTemplate getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(MsgTemplate msgTemplate) {
		this.msgTemplate = msgTemplate;
	}

	public List<Business> getBusiness() {
		return business;
	}

	public void setBusiness(List<Business> business) {
		this.business = business;
	}

	public CheckIn getCheckin() {
		return checkin;
	}

	public void setCheckin(CheckIn checkin) {
		this.checkin = checkin;
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
}
