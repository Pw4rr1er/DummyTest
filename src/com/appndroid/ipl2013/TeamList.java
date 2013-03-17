package com.appndroid.ipl2013;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamList extends Activity {
	ImageView headerClick;
	ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teamlist);
		
		
		String fontPath = "fonts/Face Your Fears.ttf";
		TextView txtTitle = (TextView) findViewById(R.id.title);
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txtTitle.setTypeface(tf);

		img1 = (ImageView) findViewById(R.id.img1);
		img2 = (ImageView) findViewById(R.id.img2);
		img3 = (ImageView) findViewById(R.id.img3);
		img4 = (ImageView) findViewById(R.id.img4);
		img5 = (ImageView) findViewById(R.id.img5);
		img6 = (ImageView) findViewById(R.id.img6);
		img7 = (ImageView) findViewById(R.id.img7);
		img8 = (ImageView) findViewById(R.id.img8);
		img9 = (ImageView) findViewById(R.id.img9);

//		headerClick = (ImageView) findViewById(R.id.header);
//
//		headerClick.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				callEvent();
//
//			}
//		});

		img1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(1);
			}
		});

		img2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(2);
			}
		});
		img3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(3);
			}
		});
		img4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(4);
			}
		});
		img5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(5);
			}
		});
		img6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(6);
			}
		});
		img7.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(7);
			}
		});
		img8.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(8);
			}
		});
		img9.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SwitchScreen(9);
			}
		});

	}

	public void SwitchScreen(int teamNumber) {
		Intent teamlistIntent = new Intent(TeamList.this, FlipperTeamList.class);
		teamlistIntent.putExtra("value", teamNumber);
		startActivity(teamlistIntent);

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
//			menuDialog = new MenuDialog(this, "teams");
//		}
//
//		menuDialog.show();
//	}
}
