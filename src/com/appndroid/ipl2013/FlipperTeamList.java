package com.appndroid.ipl2013;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.appndroid.ipl2013.SimpleGestureFilter.SimpleGestureListener;

public class FlipperTeamList extends Activity implements SimpleGestureListener {
	// public class FlipperTeamList extends Activity {
	private SimpleGestureFilter detector;
	private ViewFlipper flip = null;
	int value = 0;
	ImageView flagImage;
	TextView exampleTex;

	TextView teamFulname;
	TextView matchPalyed;
	TextView matchWon;
	TextView matchLost;
	TextView matchTied;
	TextView matchNoResult;
	TextView matchWinPer;
	TextView highScore;
	TextView lowScore;
	TextView winByRuns;
	TextView winByWkts;
	TextView partnerShip;
	TextView highScorer;
	TextView bestBowling;
	ImageView headerClick;
	teamRecords teamrecord;
	public static String selectedTeam;
	int currentLayout;
	TextView page1;
	TextView page2;
	TextView page3;
    TextView page4;
    TextView page5;
    TextView page6;
    TextView page7;
    TextView page8;
    TextView page9;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.viewflipper_layout);

		headerClick = (ImageView) findViewById(R.id.header);

		detector = new SimpleGestureFilter(this, this);
		View gestureView = (View) findViewById(R.id.gestures);

		value = getIntent().getExtras().getInt("value");

		flip = (ViewFlipper) findViewById(R.id.flip);

		View view;
		LayoutInflater inflater1 = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater1.inflate(R.layout.filpteamlist, null);

		flip.addView(view, 1);

		teamFulname = (TextView) view.findViewById(R.id.teamFulllName);
		matchPalyed = (TextView) view.findViewById(R.id.matchPlayed);
		matchWon = (TextView) view.findViewById(R.id.matchWon);
		matchLost = (TextView) view.findViewById(R.id.matchLost);
		matchTied = (TextView) view.findViewById(R.id.matchTied);
		matchNoResult = (TextView) view.findViewById(R.id.matchNoResult);
		matchWinPer = (TextView) view.findViewById(R.id.matchWinPercentage);
		highScore = (TextView) view.findViewById(R.id.hScore);
		lowScore = (TextView) view.findViewById(R.id.lScore);
		winByRuns = (TextView) view.findViewById(R.id.byRuns);
		winByWkts = (TextView) view.findViewById(R.id.byWkts);
		partnerShip = (TextView) view.findViewById(R.id.patners);
		highScorer = (TextView) view.findViewById(R.id.batsman);
		bestBowling = (TextView) view.findViewById(R.id.bowler);
		
		page1 = (TextView) view.findViewById(R.id.counter1);
		page2 = (TextView) view.findViewById(R.id.counter2);
		page3 = (TextView) view.findViewById(R.id.counter3);
        page4 = (TextView) view.findViewById(R.id.counter4);
        page5 = (TextView) view.findViewById(R.id.counter5);
        page6 = (TextView) view.findViewById(R.id.counter6);
        page7 = (TextView) view.findViewById(R.id.counter7);
        page8 = (TextView) view.findViewById(R.id.counter8);
        page9 = (TextView) view.findViewById(R.id.counter9);
        

		flagImage = (ImageView) findViewById(R.id.fliperteamIcon);
		teamrecord = new teamRecords();
		// flip.setInAnimation(this, android.R.anim.slide_in_left);
		// flip.setOutAnimation(this, android.R.anim.slide_out_right);
		currentLayout = value;
		layoutDetails(value);

//		headerClick.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				callEvent();
//			}
//		});
		if( Utils.db == null )
            Utils.getDB( this );

	}

//	MenuDialog menuDialog;
//
//	public void callEvent() {
//
//		if (menuDialog == null) {
//
//			menuDialog = new MenuDialog(this, "schedule");
//		}
//
//		menuDialog.show();
//	}

	private void layoutDetails(int nValue) {
		// TODO Auto-generated method stub

		switch (nValue) {
		case 1:
			selectedTeam = "playersCSK";
			fillData(teamrecord.getCskData());
			flagImage.setBackgroundResource(R.drawable.csk);
			flip.setDisplayedChild(1);
			page1.setBackgroundResource( R.drawable.countershape_selected );
			page2.setBackgroundResource( R.drawable.countershape );
			page9.setBackgroundResource( R.drawable.countershape );
			break;
		case 2:
			selectedTeam = "playersDD";
			fillData(teamrecord.getddData());
			flagImage.setBackgroundResource(R.drawable.dd);
			flip.setDisplayedChild(1);
			page1.setBackgroundResource( R.drawable.countershape );
            page2.setBackgroundResource( R.drawable.countershape_selected );
            page3.setBackgroundResource( R.drawable.countershape );
			break;
		case 3:
			selectedTeam = "playersKXIP";
			fillData(teamrecord.getKxipData());
			flagImage.setBackgroundResource(R.drawable.kxip);
			flip.setDisplayedChild(1);
			page2.setBackgroundResource( R.drawable.countershape );
            page3.setBackgroundResource( R.drawable.countershape_selected );
            page4.setBackgroundResource( R.drawable.countershape );
			break;
		case 4:
			selectedTeam = "playersKKR";
			fillData(teamrecord.getkkrData());
			flagImage.setBackgroundResource(R.drawable.kkr);
			flip.setDisplayedChild(1);
			page3.setBackgroundResource( R.drawable.countershape );
            page4.setBackgroundResource( R.drawable.countershape_selected );
            page5.setBackgroundResource( R.drawable.countershape );
			break;
		case 5:
			selectedTeam = "playersMI";
			fillData(teamrecord.getmiData());
			flagImage.setBackgroundResource(R.drawable.mi);
			flip.setDisplayedChild(1);
			page4.setBackgroundResource( R.drawable.countershape );
            page5.setBackgroundResource( R.drawable.countershape_selected );
            page6.setBackgroundResource( R.drawable.countershape );
			break;
		case 6:
			selectedTeam = "playersPWI";
			fillData(teamrecord.getPwiData());
			flagImage.setBackgroundResource(R.drawable.pwi);
			flip.setDisplayedChild(1);
			page5.setBackgroundResource( R.drawable.countershape );
            page6.setBackgroundResource( R.drawable.countershape_selected );
            page7.setBackgroundResource( R.drawable.countershape );
			break;
		case 7:
			selectedTeam = "playersRR";
			fillData(teamrecord.getRrData());
			flagImage.setBackgroundResource(R.drawable.rr);
			flip.setDisplayedChild(1);
			page6.setBackgroundResource( R.drawable.countershape );
            page7.setBackgroundResource( R.drawable.countershape_selected );
            page8.setBackgroundResource( R.drawable.countershape );
			break;
		case 8:
			selectedTeam = "playersRCB";
			fillData(teamrecord.getRcbData());
			flagImage.setBackgroundResource(R.drawable.rcb);
			flip.setDisplayedChild(1);
			page7.setBackgroundResource( R.drawable.countershape );
            page8.setBackgroundResource( R.drawable.countershape_selected );
            page9.setBackgroundResource( R.drawable.countershape );
			break;
		case 9:
			selectedTeam = "playersSH";
			fillData(teamrecord.getdcData());
			flagImage.setBackgroundResource(R.drawable.sh);
			flip.setDisplayedChild(1);
			page8.setBackgroundResource( R.drawable.countershape );
            page9.setBackgroundResource( R.drawable.countershape_selected );
            page1.setBackgroundResource( R.drawable.countershape );
			break;
		default:
			break;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT:
			if (ExpandablePanel.mExpanded) {
				value--;
				if (value < 1)
					value = 9;
				flip.showPrevious();
				inFromRightAnimation();
				outToLeftAnimation();
				currentLayout = value + 1;
				layoutDetails(value);
			}

			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			if (ExpandablePanel.mExpanded) {
				value++;
				if (value > 9)
					value = 1;
				flip.showNext();
				inFromLeftAnimation();
				outToRightAnimation();
				currentLayout = value - 1;
				layoutDetails(value);
			}

			break;

		}
		// Toast.makeText(this, "On Swipe Value is : " + value,
		// Toast.LENGTH_SHORT)
		// .show();
	}

	@Override
	public void onDoubleTap() {
		// Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	private void fillData(String[] teamData) {

		String[] matchSplit = teamData[1].split(":");

		teamFulname.setText(teamData[0]);
		matchPalyed.setText(matchSplit[0]);
		matchWon.setText(matchSplit[1]);
		matchLost.setText(matchSplit[2]);
		matchTied.setText(matchSplit[3]);
		matchNoResult.setText(matchSplit[4]);
		matchWinPer.setText(matchSplit[5]);
		highScore.setText("Highest Score : " + teamData[2]);
		lowScore.setText("Lowest Score : " + teamData[3]);
		winByRuns.setText("Win by " + teamData[4]);
		winByWkts.setText("Win by " + teamData[5]);
		partnerShip.setText(teamData[6]);
		highScorer.setText(teamData[7]);
		bestBowling.setText(teamData[8]);

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			//callEvent();
			return true;
		}
		else
			if (keyCode == KeyEvent.KEYCODE_BACK)
			{
				ExpandablePanel.mExpanded = true;
				//return true;
			}
		return super.onKeyUp(keyCode, event);
	}

}
