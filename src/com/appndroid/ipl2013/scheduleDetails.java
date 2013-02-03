package com.appndroid.ipl2013;

public class scheduleDetails {
	public int teamAicon;
	public int teamBicon;
	public String teamA;
	public String teamB;
	public String matchDate;
	public String matchTime;
	public String matchNumber;
	public String matchStadium;

	public scheduleDetails() {
		super();
	}

	public scheduleDetails(int p_teamAicon, int p_teamBicon, String p_teamA,
			String p_teamB, String p_matchDate, String p_matchTime,
			 String p_matchStadium) {
		super();
		this.teamAicon = p_teamAicon;
		this.teamBicon = p_teamBicon;
		this.teamA = p_teamA;
		this.teamB = p_teamB;
		this.matchDate = p_matchDate;
		this.matchTime = p_matchTime;
		//this.matchNumber = p_matchNumber;
		this.matchStadium = p_matchStadium;

	}
}