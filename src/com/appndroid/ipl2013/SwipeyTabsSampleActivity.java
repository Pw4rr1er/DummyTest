/*
 * Copyright 2011 Peter Kuterna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appndroid.ipl2013;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SwipeyTabsSampleActivity extends FragmentActivity {

	private static final String[] TITLES = { "Points Table", "Orange Cap",
			"Purple Cap" };

	private SwipeyTabs mTabs;
	private MultipleScrollViewPager mViewPager;
	List<Fragment> mfragmentList;
	private Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_swipeytab);
		
		String fontPath = "fonts/Face Your Fears.ttf";
		TextView txtHead = (TextView) findViewById(R.id.title);
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txtHead.setTypeface(tf);

		mViewPager = (MultipleScrollViewPager) findViewById(R.id.flipper);
		mTabs = (SwipeyTabs) findViewById(R.id.swipeytabs);
		mfragmentList = new Vector<Fragment>();

		bundle = new Bundle();

		Fragment pointsFragment = Fragment.instantiate(this,
				PointsTableFragment.class.getName());
		pointsFragment.setArguments(bundle);

		Fragment orangeFragment = Fragment.instantiate(this,
				OrangeCapFragment.class.getName());
		orangeFragment.setArguments(bundle);
		
		Fragment purpleFragment = Fragment.instantiate(this,
				PurpleCapFragment.class.getName());
		purpleFragment.setArguments(bundle);

		mfragmentList.add(pointsFragment);
		mfragmentList.add(orangeFragment);
		mfragmentList.add(purpleFragment);
		

		SwipeyTabsPagerAdapter adapter = new SwipeyTabsPagerAdapter(this,
				getSupportFragmentManager(), mfragmentList);
		mViewPager.setAdapter(adapter);
		mTabs.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(mTabs);
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(2);
	}

	private class SwipeyTabsPagerAdapter extends FragmentPagerAdapter implements
			SwipeyTabsAdapter {

		List<Fragment> fragmentList = null;
		private final Context mContext;

		public SwipeyTabsPagerAdapter(Context context, FragmentManager fm,
				List<Fragment> fragments) {
			super(fm);

			this.mContext = context;
			fragmentList = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		public Object instantiateItem(View container, int position) {
			LayoutInflater inflater = (LayoutInflater) container.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			int resourceId = 0;
			switch (position) {
			case 0:
				resourceId = R.layout.points_table;
				break;
			case 1:
				resourceId = R.layout.orange_cap;
				break;
			case 2:
				resourceId = R.layout.purple_cap;
				break;
			default:
				break;
			}

			View view = inflater.inflate(resourceId, null);
			((ViewPager) container).addView(view);
			return view;
		}

		public TextView getTab(final int position, SwipeyTabs root) {
			TextView view = (TextView) LayoutInflater.from(mContext).inflate(
					R.layout.swipey_tab_indicator, root, false);
			view.setText(TITLES[position]);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					mViewPager.setCurrentItem(position);
				}
			});

			return view;
		}
		
		public void destroyItem(View arg0, int arg1, Object arg2)
		{
			((ViewPager) arg0).removeView((View) arg2);
		}

	}

}