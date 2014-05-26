package com.irainbot.ui.adapter;

import java.util.ArrayList;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * viewpager 数据适配
 */
public class ViewPagerAdapter extends PagerAdapter {
	ArrayList<View> list;

	public ViewPagerAdapter(ArrayList<View> pagerList) {
		this.list = pagerList;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		try {
			((ViewPager) arg0).removeView(list.get(arg1));
		} catch (Exception e) {
		}
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		try {
			((ViewPager) arg0).addView(list.get(arg1), 0);
			return list.get(arg1);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
}
