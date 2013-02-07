package com.appndroid.ipl2013;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Schedule extends Activity implements OnItemClickListener {

	private ListView listViewObj;
	static Cursor m_cursor;
	static ListAdapter m_adapter;
	getDrawable drawable;
	String currentDialog;
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	int milli_offset = 0;
	static ListView lv;
	ListView listView;
	public static SQLiteDatabase db;
	static int listItemToSelect = -1;
	ImageView teamImage;
	ImageView stadiumImage;
	ImageView invisibleImge;
	ImageView backBtnImage;
	Dialog listDialog;

	private Integer[] teamImages = { R.drawable.csk_small, R.drawable.dd_small,
			R.drawable.kxip_small, R.drawable.kkr_small, R.drawable.mi_small,
			R.drawable.pwi_small, R.drawable.rr_small, R.drawable.rcb_small, R.drawable.dc_small };

	static final String[] title = new String[] { "Chennai Super Kings",
			"Delhi Daredevils", "Kings XI Punjab", "Kolkata Knight Riders",
			"Mumbai Indians", "Pune Warriors", "Rajasthan Royals",
			"Royal Challnegers Banglore", "Sunrisers Hyderabad" };

	static final String[] Stadiums = new String[] { "Eden Gardens, Kolkata",
			"Feroz Shah Kotla, Delhi",
			"Himachal Pradesh Cricket Association Stadium, Dharamsala",
			"JSCA International Cricket Stadium, Ranchi",
			"M. Chinnaswamy Stadium, Bangalore",
			"M.A. Chidambaram Stadium, Chennai",
			"Punjab Cricket Association Stadium, Chandigarh",
			"Rajiv Gandhi International Stadium, Hyderabad",
			"Sawai Mansingh Stadium, Jaipur",
			"Subrata Roy Sahara Stadium, Pune", "Wankhede Stadium, Mumbai" };
	private String[] winnerTeamCounter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.schedule);

		listViewObj = (ListView) findViewById(R.id.scheduleListView);

		drawable = new getDrawable();

		teamImage = (ImageView) findViewById(R.id.teams);
		stadiumImage = (ImageView) findViewById(R.id.stadium);
		invisibleImge = (ImageView) findViewById(R.id.invisible);
		backBtnImage = (ImageView) findViewById(R.id.backBtn);

		teamImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentDialog = "team";
				showdialog(v, currentDialog);
			}
		});

		stadiumImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentDialog = "stadium";
				showdialog(v, currentDialog);
			}
		});

		backBtnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backBtnImage.setVisibility(View.GONE);
				invisibleImge.setVisibility(View.GONE);

				Cursor cur = db.query("schedule", null, null, null, null, null,
						null);
				fillScheduleList(cur);

				listViewObj = (ListView) findViewById(R.id.scheduleListView);
				listViewObj.setAdapter(m_adapter);

				stadiumImage.setVisibility(View.VISIBLE);
				teamImage.setVisibility(View.VISIBLE);
			}
		});

		String fontPath = "fonts/Face Your Fears.ttf";

		// text view label
		TextView txtSchedule = (TextView) findViewById(R.id.title);

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

		// Applying font
		txtSchedule.setTypeface(tf);

	}

	private void showdialog(View view, String eventClicked) {
		// Dialog listDialog = new Dialog(this, R.style.myBackgroundStyle);

		listDialog = new Dialog(this);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = mInflater.inflate(R.layout.listview_layout, null, false);
		listDialog.setContentView(v);
		listDialog.setCancelable(true);

		if (eventClicked.equalsIgnoreCase("team")) {

			listDialog.setTitle("Select Team");

			data = new Vector<RowData>();
			for (int i = 0; i < title.length; i++) {
				try {
					rd = new RowData(i, title[i]);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				data.add(rd);
			}

			listView = (ListView) v.findViewById(R.id.listView);
			listView.setOnItemClickListener(this);
			CustomAdapter adapter = new CustomAdapter(this,
					R.layout.img_txt_layout, R.id.title, data, teamImages);
			((ListView) listView).setAdapter(adapter);
		} else {

			listDialog.setTitle("Select Stadium");
			listView = (ListView) listDialog.findViewById(R.id.listView);
			listView.setOnItemClickListener(this);
			listView.setAdapter(new ArrayAdapter<String>(this,
					R.layout.txt_layout, R.id.stadiumTextView, Stadiums));

		}
		listDialog.show();

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
		// setListAdapter(m_adapter);
		listViewObj.setAdapter(m_adapter);
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

			String strDate = drawable.getWeekShortname(cursor.getString(
					cursor.getColumnIndex("Day")).trim())
					+ " , "
					+ cursor.getString(cursor.getColumnIndex("Date")).trim()
							.replace("-13", " 2013").replace("-", " ")
					+ "   "
					+ cursor.getString(cursor.getColumnIndex("IST")).trim()
					+ " IST";
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

	private class RowData {
		protected int mId;
		protected String mTitle;

		RowData(int id, String title) {
			mId = id;
			mTitle = title;
		}

		@Override
		public String toString() {
			return mId + " " + mTitle;
		}
	}

	private class CustomAdapter extends ArrayAdapter<RowData> {

		int resValue;
		Integer[] imgid;

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects,
				Integer[] argimgid) {

			super(context, resource, textViewResourceId, objects);

			resValue = resource;
			imgid = argimgid;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			TextView title = null;
			TextView detail = null;
			ImageView i11 = null;
			RowData rowData = getItem(position);
			if (null == convertView) {
				convertView = mInflater.inflate(resValue, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);
			i11 = holder.getImage();

			i11.setImageResource(imgid[rowData.mId]);
			return convertView;
		}

		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private ImageView i11 = null;

			public ViewHolder(View row) {
				mRow = row;
			}

			public TextView gettitle() {
				if (null == title) {
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			public ImageView getImage() {
				if (null == i11) {
					i11 = (ImageView) mRow.findViewById(R.id.img);
				}
				return i11;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		stadiumImage.setVisibility(View.GONE);
		teamImage.setVisibility(View.GONE);

		if (currentDialog.equalsIgnoreCase("team")) {

			String teamName = title[arg2].toString().trim();
			teamName = drawable.getTeamShortCode(teamName);

			Cursor mCursor = db.rawQuery("SELECT * FROM schedule where teamA='"
					+ teamName + "' OR teamB='" + teamName + "'", null);
			int counter = mCursor.getCount();

			fillScheduleList(mCursor);

			listViewObj = (ListView) findViewById(R.id.scheduleListView);
			listViewObj.setAdapter(m_adapter);

		} else {

			String stadiumName = Stadiums[arg2].toString().trim();
			String[] splitName = stadiumName.split(",");

			Cursor mCursor = db.rawQuery(
					"SELECT * FROM schedule where stadium='"
							+ splitName[0].toString() + "'", null);

			fillScheduleList(mCursor);

			listViewObj = (ListView) findViewById(R.id.scheduleListView);
			listViewObj.setAdapter(m_adapter);

		}

		backBtnImage.setVisibility(View.VISIBLE);
		invisibleImge.setVisibility(View.VISIBLE);

		listDialog.dismiss();

	}

	public void fillScheduleList(Cursor cur) {

		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yy");
		String currentDate = dateFormat.format(d);
		boolean isSelected = false;

		int counter = cur.getCount();
		winnerTeamCounter = new String[counter];
		scheduleDetails team_data[] = new scheduleDetails[counter];

		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			int currentposotion = cur.getPosition();
			String matchnumber = cur.getString(0).trim();
			String day = cur.getString(cur.getColumnIndex("Day")).trim();
			String date = cur.getString(cur.getColumnIndex("Date")).trim();
			String time = cur.getString(cur.getColumnIndex("IST")).trim();
			String teamA = cur.getString(cur.getColumnIndex("TeamA")).trim();
			String teamB = cur.getString(cur.getColumnIndex("TeamB")).trim();
			String stadium = cur.getString(cur.getColumnIndex("Stadium"))
					.trim();
			String venue = cur.getString(cur.getColumnIndex("Venue")).trim();

			String winnerTeam = cur.getString(cur.getColumnIndex("WinnerTeam"))
					.trim();
			;
			winnerTeamCounter[currentposotion] = winnerTeam;

			// String matchResult =
			// cur.getString(cur.getColumnIndex("MatchResult")).trim();
			// String teamAscore =
			// cur.getString(cur.getColumnIndex("TeamA Score")).trim();
			// String teamBscore =
			// cur.getString(cur.getColumnIndex("TeamB Score")).trim();
			// String manofthematch =
			// cur.getString(cur.getColumnIndex("ManOfTheMatch")).trim();

			String matchDayDate = day + ", " + date + "  " + time + " IST";

			if (matchnumber.equals("QUALIFIER 1"))
				matchDayDate = matchDayDate + " - " + "QUALIFIER 1";
			else if (matchnumber.equals("ELIMINATOR"))
				matchDayDate = matchDayDate + " - " + "ELIMINATOR";
			else if (matchnumber.equals("QUALIFIER 2"))
				matchDayDate = matchDayDate + " - " + "QUALIFIER 2";
			else if (matchnumber.equals("FINAL"))
				matchDayDate = matchDayDate + " - " + "FINAL";

			scheduleDetails schDetail = new scheduleDetails(
					drawable.getIcon(teamA), drawable.getIcon(teamB), teamA,
					teamB, matchDayDate, time, stadium + ", " + venue);
			team_data[currentposotion] = schDetail;

			if (date.equals(currentDate) && !isSelected) {
				listItemToSelect = cur.getPosition();
				isSelected = true;
			}

			cur.moveToNext();

		}

		cur.close();
		isSelected = false;

		m_adapter = new scheduleDetailsAdapter(this,
				R.layout.cric_schedule_item, team_data);

	}
}
