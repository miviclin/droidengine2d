package com.miviclin.droidengine2d.util.time;

/**
 * Constantes de tiempo
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TimeConstants {

	public static final int NANOSECONDS_PER_SECOND = 1000000000;
	public static final int NANOSECONDS_PER_MILLISECOND = 1000000;
	public static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int HOURS_PER_DAY = 24;
	public static final int DAYS_PER_WEEK = 7;

	public static final int MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE;
	public static final int MILLISECONDS_PER_HOUR = MILLISECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	public static final int MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * HOURS_PER_DAY;
	public static final int MILLISECONDS_PER_WEEK = MILLISECONDS_PER_DAY * DAYS_PER_WEEK;

	public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	public static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
	public static final int SECONDS_PER_WEEK = SECONDS_PER_DAY * DAYS_PER_WEEK;

}
