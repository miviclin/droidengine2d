package com.miviclin.droidengine2d.util.time;

import java.util.Locale;

/**
 * Time counter.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TimeCounter {

	private long milliseconds;
	private long start;
	private boolean started;

	/**
	 * Creates a new TimeCounter.
	 */
	public TimeCounter() {
		milliseconds = 0;
		start = System.currentTimeMillis();
		started = false;
	}

	/**
	 * Resets this counter.
	 */
	public void reset() {
		milliseconds = 0;
		start = System.currentTimeMillis();
		started = false;
	}

	/**
	 * Updates this counter.<br>
	 * Updates the elapsed time by adding the time elapsed since the last update.
	 */
	public void update() {
		if (!started) {
			reset();
			started = true;
		} else {
			milliseconds = System.currentTimeMillis() - start;
		}
	}

	/**
	 * Returns the time accumulated in this counter, in milliseconds.
	 * 
	 * @return time elapsed, in milliseconds
	 */
	public long getMilliseconds() {
		return milliseconds;
	}

	/**
	 * Returns the time accumulated in this counter, in seconds.
	 * 
	 * @return time elapsed, in seconds
	 */
	public long getSeconds() {
		return milliseconds / TimeConstants.MILLISECONDS_PER_SECOND;
	}

	/**
	 * Returns the time accumulated in this counter, in minutes.
	 * 
	 * @return time elapsed, in minutes
	 */
	public long getMinutes() {
		return milliseconds / TimeConstants.MILLISECONDS_PER_MINUTE;
	}

	/**
	 * Returns the time accumulated in this counter, in hours.
	 * 
	 * @return time elapsed, in hours
	 */
	public long getHours() {
		return milliseconds / TimeConstants.MILLISECONDS_PER_HOUR;
	}

	/**
	 * Returns the time accumulated in this counter, in days.
	 * 
	 * @return time elapsed, in days
	 */
	public long getDays() {
		return milliseconds / TimeConstants.MILLISECONDS_PER_DAY;
	}

	@Override
	public String toString() {
		long days = getDays();
		long hours = getHours() % TimeConstants.HOURS_PER_DAY;
		long minutes = getMinutes() % TimeConstants.MINUTES_PER_HOUR;
		long seconds = getSeconds() % TimeConstants.SECONDS_PER_MINUTE;
		long milliseconds = this.milliseconds % TimeConstants.MILLISECONDS_PER_SECOND;
		return String.format(Locale.US, "%02d:%02d:%02d:%02d.%03d", days, hours, minutes, seconds, milliseconds);
	}

}
