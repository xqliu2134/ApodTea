package com.apod.tea.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apod.tea.R;
import com.apod.tea.bo.Business.BusinessLevels.Item;

public class MoneyView {
	private TextView parent;

	public MoneyView(Context context) {
		parent = (TextView) LayoutInflater.from(context).inflate(R.layout.text, null);
	}

	public void setText(Item item) {
		if (parent != null) {
			String name = item.getItemName() + "：" + item.getItemValue();
			parent.setText(name);
		}
	}

	public View getView() {
		return parent;
	}
}
