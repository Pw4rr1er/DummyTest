package com.appndroid.ipl2013;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkManager {

	Context mContext;
	private int m_nCommandType;
	private String currentTag;
	public static boolean isNetworkConnection = false;
	public static boolean isDataFetched = false;
	public static String isAvailable = "false", serverName = "";
	SQLiteDatabase db;

	public NetworkManager(Context context) {
		mContext = context;
		if (Utils.db == null)
			Utils.getDB(mContext);
		db = Utils.db;
	}

	public String generateString(InputStream stream) {
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader buffer = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		try {
			String cur;
			while ((cur = buffer.readLine()) != null) {
				sb.append(cur + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public class HttpAsyncConnector extends AsyncTask<String, String, String> {

		HttpClient hc;

		protected void onPreExecute() {

		}

		// automatically done on worker thread (separate from UI thread)
		protected String doInBackground(final String... args) {
			String szResponse = "";
			boolean bSuccess = false;
			HttpGet get = null;
			try {
				hc = new DefaultHttpClient();
				if (m_nCommandType == ApplicationDefines.CommandType.COMMAND_SCHEDULE) {
					get = new HttpGet(ApplicationDefines.Constants.SCHEDULE_URL);
					// Log.d("NetworkManager.HttpAsyncConnector-doInBackground()",
					// "Request is ::: "
					// + ApplicationDefines.Constants.SCHEDULE_URL);
				}

				HttpResponse rp = hc.execute(get);
				InputStream data = rp.getEntity().getContent();
				szResponse = generateString(data);
				isNetworkConnection = true;
				isDataFetched = true;
				// Log.d("NetworkManager.HttpAsyncConnector-doInBackground()",
				// "Response is ::: " + szResponse);
				return szResponse;

			}

			catch (Exception e) {
				// TODO Auto-generated catch block
				isNetworkConnection = false;
				e.printStackTrace();
				return "";
			}

		}

		// can use UI thread here
		protected void onPostExecute(final String szResponse) {
			super.onPostExecute(szResponse);
			// Log.d("NetworkManager.HttpAsyncConnector-onPostExecute()",
			// "Response is :: " + szResponse);
			if (szResponse.equals(""))
				Utils.isDocsFetched = false;
			else {
				Utils.isDocsFetched = true;
				parseResponse(szResponse);
			}

		}

		private void parseResponse(String szResponse) {
			try {

				int matchnumber = 0;
				String winnerTeam = "", matchResult = "", teamAscore = "", teamBscore = "", manofthematch = "", other1 = "", other2 = "", other3 = "";

				int pointTableID = 1;
				String Team = "", Played = "", Won = "", Lost = "", NoResult = "", Points = "", NetRunRate = "";
				String TeamA = "", TeamB = "", matchType = "";

				int orangeCapId = 1;
				String Player = "", Runs = "", HS = "", Sixes = "", Fours = "";

				int purpleCapId = 1;
				String Wkts = "", BBI = "", Maidens = "", SR = "";

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(new StringReader(szResponse));
				int eventType = xpp.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_DOCUMENT) {
						System.out.println("Start document");
					} else if (eventType == XmlPullParser.END_DOCUMENT) {
						System.out.println("End document");
					} else if (eventType == XmlPullParser.START_TAG) {
						System.out.println("Start tag " + xpp.getName());
					} else if (eventType == XmlPullParser.END_TAG) {
						System.out.println("End tag " + xpp.getName());
					} else if (eventType == XmlPullParser.TEXT) {
						System.out.println("Text " + xpp.getText());
					}
					currentTag = xpp.getName();

					// Toast.makeText(mContext, currentTag, Toast.LENGTH_LONG)
					// .show();

					if (eventType == XmlPullParser.START_TAG) {

						if (currentTag.equalsIgnoreCase("matchupdate")) {
							// Do nothing
						} else if (currentTag.equals("details")) {

							for (int attrCnt = 0; attrCnt < xpp
									.getAttributeCount(); attrCnt++) {
								String key = xpp.getAttributeName(attrCnt);
								if (key.equals("matchnumber")) {
									matchnumber = Integer.parseInt(xpp
											.getAttributeValue(attrCnt));
								} else if (key.equals("winnerTeam")) {
									winnerTeam = (xpp
											.getAttributeValue(attrCnt));
								} else if (key.equals("matchResult")) {
									matchResult = (xpp
											.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("teamAscore")) {
									teamAscore = (xpp
											.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("teamBscore")) {
									teamBscore = (xpp
											.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("manofthematch")) {
									manofthematch = (xpp
											.getAttributeValue(attrCnt)
											.toString());
								}
							}
						} else if (currentTag.equalsIgnoreCase("pointTable")) {
							// Do nothing
						} else if (currentTag.equalsIgnoreCase("teamStanding")) {

							Log.d("MY APP BATSMEN", "WE R IN");

							for (int attrCnt = 0; attrCnt < xpp
									.getAttributeCount(); attrCnt++) {
								String key = xpp.getAttributeName(attrCnt);
								if (key.equals("Team")) {
									Team = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("Played")) {
									Played = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("Won")) {
									Won = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Lost")) {
									Lost = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("NoResult")) {
									NoResult = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Points")) {
									Points = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("NetRunRate")) {
									NetRunRate = (xpp
											.getAttributeValue(attrCnt)
											.toString());
								}

							}
						} else if (currentTag.equalsIgnoreCase("orangeCap")) {
							// Do nothing
						} else if (currentTag
								.equalsIgnoreCase("orangeCapHolder")) {

							for (int attrCnt = 0; attrCnt < xpp
									.getAttributeCount(); attrCnt++) {
								String key = xpp.getAttributeName(attrCnt);
								if (key.equals("Player")) {
									Player = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("Runs")) {
									Runs = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("HS")) {
									HS = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Sixes")) {
									Sixes = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Fours")) {
									Fours = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Team")) {
									Team = (xpp.getAttributeValue(attrCnt)
											.toString());
								}
							}

						} else if (currentTag.equalsIgnoreCase("purpleCap")) {
							// Do nothing
						} else if (currentTag
								.equalsIgnoreCase("purpleCapHolder")) {

							for (int attrCnt = 0; attrCnt < xpp
									.getAttributeCount(); attrCnt++) {
								String key = xpp.getAttributeName(attrCnt);
								if (key.equals("Player")) {
									Player = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("Wkts")) {
									Wkts = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("BBI")) {
									BBI = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Maidens")) {
									Maidens = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("SR")) {
									SR = (xpp.getAttributeValue(attrCnt)
											.toString());
								} else if (key.equals("Team")) {
									Team = (xpp.getAttributeValue(attrCnt)
											.toString());
								}

							}

						} else if (currentTag
								.equalsIgnoreCase("notificationServer")) {
							// Do nothing
						} else if (currentTag.equalsIgnoreCase("serverdetails")) {

							// for (int attrCnt = 0; attrCnt < xpp
							// .getAttributeCount(); attrCnt++) {
							// String key = xpp.getAttributeName(attrCnt);
							// if (key.equals("isAvailable")) {
							// isAvailable = (xpp
							// .getAttributeValue(attrCnt));
							// } else if (key.equals("serverName")) {
							// serverName = (xpp
							// .getAttributeValue(attrCnt));
							// }
							//
							// if (!serverName.equalsIgnoreCase("")) {
							// //
							// Crick20Activity.setIsPushNotificationAvailable(
							// // this, true );
							// Crick20Activity.setPushNotificationServer(
							// mContext, serverName);
							// } else {
							// //
							// Crick20Activity.setIsPushNotificationAvailable(
							// // this, false );
							// Crick20Activity.setPushNotificationServer(
							// mContext, serverName);
							// }
							//
							// }

						} else if (currentTag.equals("eliminators")) {
							// Do nothing
						} else if (currentTag.equalsIgnoreCase("eliminator")) {

							for (int attrCnt = 0; attrCnt < xpp
									.getAttributeCount(); attrCnt++) {
								String key = xpp.getAttributeName(attrCnt);
								if (key.equals("TeamA")) {
									TeamA = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("TeamB")) {
									TeamB = (xpp.getAttributeValue(attrCnt));
								} else if (key.equals("match")) {
									matchType = (xpp.getAttributeValue(attrCnt));
								}
							}
						}

					} else if (eventType == XmlPullParser.TEXT) {
						String asd = "";
						// result = xpp.getText();
					} else if (eventType == XmlPullParser.END_TAG) {
						if (currentTag.equalsIgnoreCase("details")) {
							ContentValues values = new ContentValues();

							values.put("winnerTeam", winnerTeam);
							values.put("matchResult", matchResult);
							values.put("teamAscore", teamAscore);
							values.put("teamBscore", teamBscore);
							values.put("manofthematch", manofthematch);

							int i = db
									.update("schedule", values, "_id=?",
											new String[] { Long
													.toString(matchnumber) });

							Log.d("SCHEDULE DBUPDATE", "matchnumber is  :: "
									+ matchnumber + " Schedule update : "
									+ String.valueOf(i));

						} else if (currentTag.equalsIgnoreCase("teamStanding")) {

							ContentValues values = new ContentValues();

							values.put("Team", Team);
							values.put("Played", Played);
							values.put("Won", Won);
							values.put("Lost", Lost);
							values.put("NoResult", NoResult);
							values.put("Points", Points);
							values.put("NetRunRate", NetRunRate);

							int i = db
									.update("pointsTable", values, "_id=?",
											new String[] { Long
													.toString(pointTableID) });

							Log.d("POINT TABLE DBUPDATE", " Records update : "
									+ String.valueOf(i));

							pointTableID++;

						} else if (currentTag
								.equalsIgnoreCase("orangeCapHolder")) {

							ContentValues values = new ContentValues();

							values.put("_id", orangeCapId);
							values.put("Player", Player);
							values.put("Runs", Runs);
							values.put("HS", HS);
							values.put("Sixes", Sixes);
							values.put("Fours", Fours);
							values.put("Team", Team);

							int i = db.update("orangeCap", values, "_id='"
									+ orangeCapId + "'", null);

							// int i = Crick20Activity.db
							// .update("orangeCap", values, "_id=?",
							// new String[] { Long
							// .toString(ornageCapId) });

							Log.d("Ornage CAP TABLE DBUPDATE",
									" Records update : " + String.valueOf(i));

							orangeCapId++;

						} else if (currentTag
								.equalsIgnoreCase("purpleCapHolder")) {

							ContentValues values = new ContentValues();

							values.put("Player", Player);
							values.put("Wkts", Wkts);
							values.put("BBI", BBI);
							values.put("Maidens", Maidens);
							values.put("SR", SR);
							values.put("Team", Team);

							int i = db.update("purpleCap", values, "_id='"
									+ purpleCapId + "'", null);

							Log.d("purple CAP TABLE DBUPDATE",
									" Records update : " + String.valueOf(i));

							purpleCapId++;

						} else if (currentTag.equalsIgnoreCase("eliminator")) {

							if (!TeamA.equals("") && !TeamB.equals("")) {
								ContentValues values = new ContentValues();

								values.put("TeamA", TeamA);
								values.put("TeamB", TeamB);

								int i = db.update("schedule", values,
										"Match=?", new String[] { matchType });

							}
						}
					}
					eventType = xpp.next();
				}

			}

			catch (Exception e) {

				Log.d("NetworkManager.DBUPDATE",
						"EXCEPTION IS :: " + e.getMessage());
				e.printStackTrace();
			}
			// Schedule.reloadView( mContext );
		}

		public void setTaskParams(int nCommandType) {
			m_nCommandType = nCommandType;
		}
	}

}
