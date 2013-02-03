package com.appndroid.ipl2013;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Schedule extends ListActivity {

	static Cursor m_cursor;
	static ListAdapter m_adapter;
	getDrawable drawable;
	int milli_offset = 0;
	static ListView lv;
	public static SQLiteDatabase db;
	static int listItemToSelect = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.schedule);

		lv = getListView();
		drawable = new getDrawable();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (db != null)
			db.close();
	};

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.d("schedule", "on restart");
		super.onRestart();
		db = openOrCreateDatabase("ipl2013.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		m_cursor = db.rawQuery("select * from schedule", null);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		db = openOrCreateDatabase("ipl2013.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		m_cursor = db.rawQuery("select * from schedule", null);
		m_cursor.moveToFirst();
		m_adapter = new scheduleAdapter(this, m_cursor, false);
		setListAdapter(m_adapter);
		m_cursor.moveToFirst();
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		String currentDate = dateFormat.format(d);
		boolean isSelected = false;
		do {
			String date = m_cursor.getString(m_cursor.getColumnIndex("Date"))
					.trim();
			if (date.equals(currentDate) && !isSelected) {
				listItemToSelect = m_cursor.getPosition();
				isSelected = true;
				break;
			}

		} while (m_cursor.moveToNext());
		if (listItemToSelect > -1)
			lv.setSelection(listItemToSelect);
	}

	public class scheduleAdapter extends CursorAdapter {
		private LayoutInflater inflater;

		public scheduleAdapter(Context context, Cursor cursor,
				boolean autoRequery) {
			super(context, cursor, autoRequery);
			// TODO Auto-generated constructor stub
			inflater = LayoutInflater.from(context);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			ImageView imgTeamA = (ImageView) view.findViewById(R.id.imgTeamA);
			ImageView imgTeamB = (ImageView) view.findViewById(R.id.imgTeamB);
			TextView txtTeamA = (TextView) view.findViewById(R.id.txtTeamA);
			TextView txtTeamB = (TextView) view.findViewById(R.id.txtTeamB);
			TextView txtStadium = (TextView) view.findViewById(R.id.txtStadium);
			TextView txtResult = (TextView) view.findViewById(R.id.txtResult);
			TextView txtDate = (TextView) view.findViewById(R.id.txtDateTime);

			String szTeamA = cursor.getString(cursor.getColumnIndex("TeamA"));
			imgTeamA.setImageResource(drawable.getIcon(szTeamA));
			String szTeamB = cursor.getString(cursor.getColumnIndex("TeamB"));
			imgTeamB.setImageResource(drawable.getIcon(szTeamB));
			txtTeamA.setText(szTeamA);
			txtTeamB.setText(szTeamB);

			String strResult = cursor.getString(
					cursor.getColumnIndex("MatchResult")).trim();
			if (strResult.length() > 1) {
				txtResult.setVisibility(view.VISIBLE);
				txtResult.setText(strResult);
			}

			String strStadium = cursor.getString(
					cursor.getColumnIndex("Stadium")).trim()
					+ ","
					+ cursor.getString(cursor.getColumnIndex("Venue")).trim();
			txtStadium.setText(strStadium);

			String strDate = drawable.getWeekShortname(cursor.getString(cursor.getColumnIndex("Day"))
					.trim())
					+ " , "
					+ cursor.getString(cursor.getColumnIndex("Date")).trim().replace("-13"," 2013").replace("-"," ")
					+"   "+cursor.getString(cursor.getColumnIndex("IST")).trim()+" IST";
			txtDate.setText(strDate);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.cric_schedule_item, parent,
					false);
			return view;
		}

	}

	@Override
	protected void onListItemClick(android.widget.ListView l, View v,
			int nPosition, long id) {

	}

}
