package com.appndroid.ipl2013;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OrangeCapFragment extends Fragment {

	SQLiteDatabase db;
	private LayoutInflater inflater;
	private View myTestView;
	private ListView lv;
	getDrawable drawable;
	fillList ptList;
	String[] from;
	int[] to;
	Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		myTestView = getLayoutInflater(savedInstanceState).inflate(
				R.layout.orange_cap, null);
		
		to = new int[] { R.id.stat_item1, R.id.stat_item2, R.id.stat_item3,
				R.id.stat_item4, R.id.stat_item5, R.id.stat_item6 };
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.orange_cap, null);
		drawable = new getDrawable();
		lv = (ListView) root.findViewById(R.id.lstorangeCap);
		lv.setEnabled(false);
		ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
				"Please wait...", true);
		fillRecordTask task = new fillRecordTask();
		task.dialog = dialog;
		task.execute();
		return root;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	public void fillData() {
		@SuppressWarnings("unchecked")
		SimpleAdapter adapter = new overrideAdapter(getActivity(),
				ptList.getFilledList(), R.layout.orangepurplecap_layout, from,
				to, "captab");
		lv.setAdapter(adapter);

	}
	
	public int getDataFromDB() {

		db = getActivity().openOrCreateDatabase("ipl2013.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		from = new String[] { "Player", "Runs", "HS", "4's", "6's", "Team" };

		ptList = new fillList(from);
		Cursor cur = db.rawQuery("select * from OrangeCap", null);
		ptList.fillRecordList(cur, ptList, "recordTab");

		return cur.getCount();

	}
	
	private class fillRecordTask extends AsyncTask<String, Void, String> {

		ProgressDialog dialog = null;
		int recordCount;

		// can use UI thread here
		protected void onPreExecute() {

		}

		// automatically done on worker thread (separate from UI thread)
		protected String doInBackground(final String... args) {
			recordCount = getDataFromDB();
			return "";
		}

		// can use UI thread here
		protected void onPostExecute(final String result) {
			if (dialog != null) {
				dialog.dismiss();
				dialog = null;
			}

			fillData();

			
		}

	}

}

