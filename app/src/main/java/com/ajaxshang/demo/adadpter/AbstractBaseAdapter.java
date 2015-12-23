package com.ajaxshang.demo.adadpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类adapter
 * 
 */
public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mListData;

	public AbstractBaseAdapter(Context context) {
		this.mContext = context;
		mListData = new ArrayList<T>();
	}

	public void delete(int position) {
		mListData.remove(position);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	public List<T> getList() {
		return mListData;
	}

	public void remove(T t) {
		if (mListData.contains(t)) {
			mListData.remove(t);
			notifyDataSetChanged();
		}
	}

	public void removeAll() {
		mListData.clear();
		notifyDataSetChanged();
	}

	public void addAll(List<T> data) {
		if (data == null)
			return;
		mListData.clear();
		mListData.addAll(data);
		notifyDataSetChanged();
	}

	public void add(T t) {
		mListData.add(t);
		notifyDataSetChanged();
	}

	public void clear() {
		mListData.clear();
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if (position > mListData.size()) {
			return null;
		}
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(getItemResource(), null);
		}
		return getItemView(position, convertView);
	}

	public abstract int getItemResource();

	public abstract View getItemView(int position, View convertView);
}
