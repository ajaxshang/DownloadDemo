package com.ajaxshang.demo.adadpter;

import android.util.SparseArray;
import android.view.View;

/**
 * 
 * 项目名称:bamboo_mat<br/>
 * 类名称:ViewHolder<br/>
 * 描述:ViewHolder的简单用法<br/>
 * 
 * 用法：<br/>
 * 
 * public View getView(int position, View convertView, ViewGroup parent) {<br/>
 * 
 * &nbsp;&nbsp;&nbsp;&nbsp;if (convertView == null) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;convertView =
 * LayoutInflater.from(context).inflate(R.layout.banana_phone, parent, false);<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;ImageView bananaView = <font color="#FF0000">
 * ViewHolder.get(convertView, R.id.banana);</font><br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;TextView phoneView = <font
 * color="#FF0000">ViewHolder.get(convertView, R.id.phone);</font><br/>
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;BananaPhone bananaPhone = getItem(position); <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;phoneView.setText(bananaPhone.getPhone());<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bananaView.setImageResource(bananaPhone.getBanana());
 * <br/>
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;return convertView;<br/>
 * }<br/>
 * 
 * @author:BaoZhiYuan
 * @Date:2014-8-24上午9:51:51
 */
public class ViewHolder {

	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;

	}
}
