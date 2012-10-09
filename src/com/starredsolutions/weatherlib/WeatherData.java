package com.starredsolutions.weatherlib;

import java.io.Serializable;
/**
 * 
 */

/**
 * @author juan
 *
 */
public class WeatherData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8127534328254035385L;
	public long timeStamp = 0;
	public String locationName;
	public String weather;
	public String relativeHumidity;
	/**
	 * Celcius
	 */
	public float temperature;
	public String windDirection;
	/**
	 * KMH
	 */
	public float windSpeed;
	/**
	 * Millibars
	 * "Presión Atmosférica"
	 */
	public float pressure;
	/**
	 * Celcius
	 * "Sensacion Térmica"
	 */
	public float feelslike;
	/**
	 * KM
	 */
	public float visibility;
	public String iconName;
	public String iconUrl;
	
	public Forecast[] forecast;
	
	 @Override
	 public String toString() {
		 return String.format("Location Name: %s, Weather: %s, Temperature %f", this.locationName,this.weather,this.temperature);
	 }
}
