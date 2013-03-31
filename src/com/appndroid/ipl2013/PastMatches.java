package com.appndroid.ipl2013;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PastMatches extends Activity {
	
	public static SQLiteDatabase db;
	ImageView navigationImage;
	MenuDialog menuDialog;

    public void callEvent()
    {

        // if (menuDialog == null) {

        menuDialog = new MenuDialog( this, "past_matches" );
        // }
        menuDialog.setCancelable( true );
        menuDialog.setCanceledOnTouchOutside( true );
        menuDialog.show();
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.past_matches);
		
		String fontPath = "fonts/Face Your Fears.ttf";
		TextView txtTitle = (TextView) findViewById(R.id.title);
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txtTitle.setTypeface(tf);

		Utils.setContext( this );
		int schId = getIntent().getExtras().getInt("schId");
		db = openOrCreateDatabase( "ipl2013.db", SQLiteDatabase.CREATE_IF_NECESSARY, null );

		try {

			Cursor mCursor = db.rawQuery(
					"SELECT * FROM schedule where _id = ?",
					new String[] { String.valueOf(schId) });

			fillData(mCursor);

		} catch (Exception e) {
			// TODO: handle exception
			// Log.d("SCORES", "EXCEPTION IS :: " + e.getMessage());
			e.printStackTrace();
		}
		ImageView headerClick = (ImageView) findViewById(R.id.header);
//		headerClick.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				callEvent();
//			}
//		});
		navigationImage = (ImageView) findViewById( R.id.nav );
        navigationImage.setOnClickListener( new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callEvent();
				
			}
        	
        });

	}
	
	@Override
    public boolean onKeyUp( int keyCode, KeyEvent event )
    {
        if( keyCode == KeyEvent.KEYCODE_MENU )
            callEvent();
        return super.onKeyUp( keyCode, event );
    }

	public void fillData(Cursor cur) {

		
		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			int currentposotion = cur.getPosition();
			
			String date = cur.getString(cur.getColumnIndex("Date")).trim();
			String teamA = cur.getString(cur.getColumnIndex("TeamA")).trim();
			String teamB = cur.getString(cur.getColumnIndex("TeamB")).trim();
			String stadium = cur.getString(cur.getColumnIndex("Stadium")).trim();
			String venue = cur.getString(cur.getColumnIndex("Venue")).trim();
			String winner_Team = cur.getString(cur.getColumnIndex("WinnerTeam")).trim();
			String match_Result = cur.getString(cur.getColumnIndex("MatchResult")).trim();
			String teamA_score = cur.getString(cur.getColumnIndex("TeamAScore")).trim();
			String teamB_score = cur.getString(cur.getColumnIndex("TeamBScore")).trim();
			String man_of_the_match = cur.getString(cur.getColumnIndex("ManOfTheMatch")).trim();
			
			
			getDrawable iconObj = new getDrawable();
			
			ImageView teamAicon = (ImageView) findViewById(R.id.teamAicon);
			ImageView teamBicon = (ImageView) findViewById(R.id.teamBicon);
			
			ImageView winerTagA = (ImageView) findViewById(R.id.winerTagA);
			ImageView winerTagB = (ImageView) findViewById(R.id.winerTagB);
			
			TextView teamAscore = (TextView) findViewById(R.id.teamAscore);
			TextView teamBscore = (TextView) findViewById(R.id.teamBscore);
			TextView matchVenue  = (TextView) findViewById(R.id.matchVenue);
			
			TextView manOfTheMatch  = (TextView) findViewById(R.id.manOfTheMatch);
			TextView matchDetails  = (TextView) findViewById(R.id.scorematchDetails);
			
			manOfTheMatch.setVisibility(View.GONE);
			matchDetails.setVisibility(View.GONE);
			winerTagA.setVisibility(View.GONE);
			winerTagB.setVisibility(View.GONE);
		
			
			teamAicon.setBackgroundResource(iconObj.getIcon(teamA));
			teamBicon.setBackgroundResource(iconObj.getIcon(teamB));
			
			teamAscore.setText(teamA_score);
			teamBscore.setText(teamB_score);
			
			manOfTheMatch.setVisibility(View.VISIBLE);
			matchDetails.setVisibility(View.VISIBLE);
			matchVenue.setText(stadium + ", "+venue);
			matchDetails.setText(match_Result);
			manOfTheMatch.setText("Man of the match : " + man_of_the_match);
			
			if(winner_Team.equalsIgnoreCase("teamA"))
			{
				winerTagA.setVisibility(View.VISIBLE);
				winerTagA.setBackgroundResource(R.drawable.winner);
			}
			else
			{
				winerTagB.setVisibility(View.VISIBLE);
				winerTagB.setBackgroundResource(R.drawable.winner);
			}
			
			cur.moveToNext();
		}

		cur.close();

	}
	
//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_MENU) {
//			callEvent();
//			return true;
//		}
//		return super.onKeyUp(keyCode, event);
//	}
	
//	MenuDialog menuDialog;
//
//	public void callEvent() {
//
//		if (menuDialog == null) {
//
//			menuDialog = new MenuDialog(this, "scores");
//		}
//      menuDialog.setCancelable( true );
//      menuDialog.setCanceledOnTouchOutside( true );
//		menuDialog.show();
//	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 if( db != null )
	            db.close();
	}

}

