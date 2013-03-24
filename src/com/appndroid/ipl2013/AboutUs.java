package com.appndroid.ipl2013;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutUs extends Activity {

	private String appLink = "";
	String apppackage = "";
	ImageView navigationImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_us);

		apppackage = getApplicationContext().getPackageName();
		appLink = getResources().getString(R.string.googlePlayLink).trim();
		appLink = (appLink + apppackage).trim();

		String fontPath = "fonts/Face Your Fears.ttf";
		TextView txtSchedule = (TextView) findViewById(R.id.title);
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txtSchedule.setTypeface(tf);

		try {
			String app_ver = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).versionName;

			TextView versionTxt = (TextView) findViewById(R.id.version);
			versionTxt.setText("( "
					+ getResources().getString(R.string.version_txt) + " "
					+ app_ver + " )");
		} catch (NameNotFoundException e) {

		}

		TextView poweredBy = (TextView) findViewById(R.id.poweredby);
		poweredBy.setText(getResources().getString(R.string.poweredby_txt)
				+ " - " + getResources().getString(R.string.companyName_txt));

		RelativeLayout relShare = (RelativeLayout) findViewById(R.id.rel_share);
		relShare.setOnClickListener(new OnOptionSelect(this));

		RelativeLayout relRate = (RelativeLayout) findViewById(R.id.rel_rate);
		relRate.setOnClickListener(new OnOptionSelect(this));

		RelativeLayout relFeedback = (RelativeLayout) findViewById(R.id.rel_feedback);
		relFeedback.setOnClickListener(new OnOptionSelect(this));
		
		navigationImage = (ImageView) findViewById(R.id.nav);
		navigationImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callEvent();

			}
		});

	}
	
	MenuDialog menuDialog;

    public void callEvent()
    {

        // if (menuDialog == null) {

        menuDialog = new MenuDialog( this, "about" );
        // }
        menuDialog.setCancelable( true );
        menuDialog.setCanceledOnTouchOutside( true );
        menuDialog.show();
    }
    
    @Override
    public boolean onKeyUp( int keyCode, KeyEvent event )
    {
        if( keyCode == KeyEvent.KEYCODE_MENU )
            callEvent();
        return super.onKeyUp( keyCode, event );
    }

	public void createShareIntent() {

		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		String msgToShare = getResources().getString(R.string.CheckOut) + " "
				+ appLink;
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, msgToShare);
		startActivity(shareIntent);
	}

	public void redirectToGooglePlay() {
		Log.d("MyTag", "MyTag URL : " + appLink);
		this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
				.parse("market://details?id=" + apppackage)));
	}

	public void sendEmail() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("plain/text");
		i.putExtra(
				android.content.Intent.EXTRA_EMAIL,
				new String[] { getResources().getString(R.string.feedBackEmail) });
		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "[Feedback] IPL 2013");
		startActivity(Intent.createChooser(i, "Send email"));
	}

	private class OnOptionSelect implements OnClickListener {

		AboutUs mact;

		public OnOptionSelect(AboutUs act) {
			// TODO Auto-generated constructor stub
			mact = act;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rel_share:
				mact.createShareIntent();
				break;
			case R.id.rel_rate:
				mact.redirectToGooglePlay();
				break;
			case R.id.rel_feedback:
				sendEmail();
				break;
			default:
				break;
			}

		}

	}

}
