package com.starredsolutions.weatherlib;

import java.io.Serializable;

/**
 * 
 */

/**
 * @author juan
 *
 */
public class Forecast implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2266995121866360210L;
	public String day;
	public String description;
	public float temperatureHigh;
	public float temperatureLow;
	public String iconName;
	public String iconUrl;
}
