package com.miviclin.droidengine2d.util.time;

import java.util.Locale;

/**
 * Contador de tiempo
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TimeCounter {
	
	private long milliseconds;
	private long start;
	private boolean started;
	
	/**
	 * Crea un contador de tiempo inicializado a 0
	 */
	public TimeCounter() {
		milliseconds = 0;
		start = System.currentTimeMillis();
		started = false;
	}
	
	/**
	 * Reinicia el contador
	 */
	public void reset() {
		milliseconds = 0;
		start = System.currentTimeMillis();
		started = false;
	}
	
	/**
	 * Actualiza el contador
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
	 * Devuelve el tiempo almacenado en el contador, en milisegundos.
	 * 
	 * @return tiempo en milisegundos
	 */
	public long getMilliseconds() {
		return milliseconds;
	}
	
	/**
	 * Devuelve el tiempo almacenado en el contador, en segundos.
	 * 
	 * @return tiempo en segundos
	 */
	public long getSeconds() {
		return milliseconds / TimeConstants.MILLISECONDS_PER_SECOND;
	}
	
	/**
	 * Devuelve el tiempo almacenado en el contador, en minutos.
	 * 
	 * @return tiempo en minutos
	 */
	public long getMinutes() {
		return milliseconds / (TimeConstants.MILLISECONDS_PER_SECOND * TimeConstants.SECONDS_PER_MINUTE);
	}
	
	/**
	 * Devuelve el tiempo almacenado en el contador, en horas.
	 * 
	 * @return tiempo en horas
	 */
	public long getHours() {
		return milliseconds / (TimeConstants.MILLISECONDS_PER_SECOND * TimeConstants.SECONDS_PER_MINUTE * TimeConstants.MINUTES_PER_HOUR);
	}
	
	/**
	 * Devuelve el tiempo almacenado en el contador, en dias.
	 * 
	 * @return tiempo en dias
	 */
	public long getDays() {
		int millisecondsPerMinute = TimeConstants.MILLISECONDS_PER_SECOND * TimeConstants.SECONDS_PER_MINUTE;
		return milliseconds / (millisecondsPerMinute * TimeConstants.MINUTES_PER_HOUR * TimeConstants.HOURS_PER_DAY);
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
