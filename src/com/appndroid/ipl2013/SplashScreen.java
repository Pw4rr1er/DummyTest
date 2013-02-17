package com.appndroid.ipl2013;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.appndroid.ipl2013.NetworkManager.HttpAsyncConnector;

public class SplashScreen extends Activity {
	
	private NetworkManager networkmanager;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Create an object of type SplashHandler
		SplashHandler mHandler = new SplashHandler();
		setContentView(R.layout.spalsh_screen);
		// HideActionBar();
		
		//creating database
		new CopyDBTask().execute();

		// Create a Message object
		Message msg = new Message();

		// Assign a unique code to the message, which is used to identify the
		// message in Handler class.
		msg.what = 0;

		mHandler.sendMessageDelayed(msg, 3000); // 3 sec delay
	}

	private class SplashHandler extends Handler {

		// This method is used to handle received messages
		public void handleMessage(Message msg) {
			// switch to identify the message by its code
			switch (msg.what) {
			default:
			case 0:
				super.handleMessage(msg);
				Intent i = new Intent(SplashScreen.this, Schedule.class);
				startActivity(i);

				SplashScreen.this.finish();
			}
		}
	}

	private class CopyDBTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			copyFromAssetsDatabase();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			fetchDocs();

		}
	}

	public void copyFromAssetsDatabase() {
		// TODO Auto-generated method stub

		DataBaseHelper myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();
			Log.d("MyTest", "Databse Created");

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}

	}
	
	private void fetchDocs()
    {
        networkmanager = new NetworkManager( SplashScreen.this );
        if( !NetworkManager.isDataFetched )
        {

            HttpAsyncConnector httpConnect = networkmanager.new HttpAsyncConnector();
            httpConnect.setTaskParams( ApplicationDefines.CommandType.COMMAND_SCHEDULE );
            httpConnect.execute();
        }
    }

}