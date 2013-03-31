package com.appndroid.ipl2013;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IconicAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final String[] check;

	public IconicAdapter(Context context, String[] values, String[] check) {
		super(context, R.layout.img_txt_layout, values);
		this.context = context;
		this.values = values;
		this.check = check;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.img_txt_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.title);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

		textView.setText(values[position]);
		String s = check[position];

		if (s != null) {
			if (s.toLowerCase().contains("batsmen")) {
				imageView.setImageResource(R.drawable.batsmen);
			} else if (s.toLowerCase().contains("captain")) {
				imageView.setImageResource(R.drawable.captain);
			} else if (s.toLowerCase().contains("bowler")) {
				imageView.setImageResource(R.drawable.bowler);
			} else if (s.toLowerCase().contains("allrounder")) {
				imageView.setImageResource(R.drawable.allrounder);
			} else if (s.toLowerCase().contains("wkt")) {
				imageView.setImageResource(R.drawable.wkt);
			}
		}
		else
		{
			RelativeLayout.LayoutParams lastTxtParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lastTxtParams.setMargins(120, 15, 0,15);
			textView.setPadding(0, 0, 0, 15);
			textView.setLayoutParams(lastTxtParams);
		}

		return rowView;
	}
}
