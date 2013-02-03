package com.appndroid.ipl2013;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class scheduleDetailsAdapter extends ArrayAdapter<scheduleDetails> {

	Context context;
	int layoutResourceId;
	scheduleDetails data[] = null;

	public scheduleDetailsAdapter(Context context, int layoutResourceId,
			scheduleDetails[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		detailsHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new detailsHolder();
			holder.teamA_imgIcon = (ImageView) row.findViewById(R.id.imgTeamA);
			holder.teamB_imgIcon = (ImageView) row.findViewById(R.id.imgTeamB);
			holder.txt_matchDate = (TextView) row.findViewById(R.id.txtDateTime);
//			holder.txt_matchNumber = (TextView) row
//					.findViewById(R.id.matchNumber);
			holder.txt_teamA = (TextView) row.findViewById(R.id.txtTeamA);
			holder.txt_teamB = (TextView) row.findViewById(R.id.txtTeamB);
			//holder.txt_matchTime = (TextView) row.findViewById(R.id.matchTime);
			holder.txt_matchStadium = (TextView) row
					.findViewById(R.id.txtStadium);

			row.setTag(holder);
		} else {
			holder = (detailsHolder) row.getTag();
		}

		scheduleDetails objTeamDetail = data[position];

		holder.teamA_imgIcon.setImageResource(objTeamDetail.teamAicon);
		holder.teamB_imgIcon.setImageResource(objTeamDetail.teamBicon);

		holder.txt_teamA.setText(objTeamDetail.teamA);
		holder.txt_teamB.setText(objTeamDetail.teamB);
		holder.txt_matchDate.setText(objTeamDetail.matchDate);
		//holder.txt_matchTime.setText(objTeamDetail.matchTime);
//		holder.txt_matchNumber.setText(objTeamDetail.matchNumber);
		holder.txt_matchStadium.setText(objTeamDetail.matchStadium);

		return row;
	}

	static class detailsHolder {
		ImageView teamA_imgIcon;
		ImageView teamB_imgIcon;

		TextView txt_teamA;
		TextView txt_teamB;
		TextView txt_matchDate;
		TextView txt_matchTime;
//		TextView txt_matchNumber;
		TextView txt_matchStadium;

	}
}