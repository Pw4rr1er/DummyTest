package com.appndroid.ipl2013;

public class getDrawable {

	public int getIcon(String teamName) {

		if (teamName.toLowerCase().trim().equals("csk")
				|| teamName.toLowerCase().trim().equals("chennai super kings"))
			return R.drawable.csk;
		else if (teamName.toLowerCase().trim().equals("sh")
				|| teamName.toLowerCase().trim().equals("sunrisers hyderabad"))
			return R.drawable.sh;
		else if (teamName.toLowerCase().trim().equals("dd")
				|| teamName.toLowerCase().trim().equals("delhi daredevils"))
			return R.drawable.dd;
		else if (teamName.toLowerCase().trim().equals("kxip")
				|| teamName.toLowerCase().trim().contains("kings xi punjab"))
			return R.drawable.kxip;
		else if (teamName.toLowerCase().trim().equals("kkr")
				|| teamName.toLowerCase().trim()
						.equals("kolkata knight riders"))
			return R.drawable.kkr;
		else if (teamName.toLowerCase().trim().equals("mi")
				|| teamName.toLowerCase().trim().equals("mumbai indians"))
			return R.drawable.mi;
		else if (teamName.toLowerCase().trim().equals("pw")
				|| teamName.toLowerCase().trim().equals("pune warriors"))
			return R.drawable.pwi;
		else if (teamName.toLowerCase().trim().equals("rr")
				|| teamName.toLowerCase().trim().equals("rajasthan royals"))
			return R.drawable.rr;
		else if (teamName.toLowerCase().trim().equals("rcb")
				|| teamName.toLowerCase().trim()
						.equals("royal challengers bangalore"))
			return R.drawable.rcb;
		else if (teamName.toLowerCase().trim().equals("appicon"))
			return R.drawable.ic_launcher;
		else
			return R.drawable.ipl2013;
	}

	public String getTeamShortCode(String teamName) {
		if (teamName.equalsIgnoreCase("Chennai Super Kings"))
			return "CSK";
		else if (teamName.equalsIgnoreCase("Sunrisers Hyderabad"))
			return "SH";
		else if (teamName.equalsIgnoreCase("Delhi Daredevils"))
			return "DD";
		else if (teamName.equalsIgnoreCase("Kings XI Punjab"))
			return "KXIP";
		else if (teamName.equalsIgnoreCase("Kolkata Knight Riders"))
			return "KKR";
		else if (teamName.equalsIgnoreCase("Mumbai Indians"))
			return "MI";
		else if (teamName.equalsIgnoreCase("Pune Warriors"))
			return "PW";
		else if (teamName.equalsIgnoreCase("Rajasthan Royals"))
			return "RR";
		else if (teamName.equalsIgnoreCase("Royal challengers bangalore"))
			return "RCB";
		else if (teamName.equalsIgnoreCase("Kochi Tuskers Kerala"))
			return "KTK";
		else
			return "";

	}

	public String getWeekShortname(String day) {

		if (day.toLowerCase().equals("monday"))
			return "Mon";
		else if (day.toLowerCase().equals("tuesday"))
			return "Tue";
		else if (day.toLowerCase().equals("wednesday"))
			return "Wed";
		else if (day.toLowerCase().equals("thursday"))
			return "Thu";
		else if (day.toLowerCase().equals("friday"))
			return "Fri";
		else if (day.toLowerCase().equals("saturday"))
			return "Sat";
		else if (day.toLowerCase().equals("sunday"))
			return "Sun";
		else
			return day;

	}

}
