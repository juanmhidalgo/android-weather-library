/**
 * 
 */
package com.starredsolutions.weatherlib;

import android.content.Context;
import android.location.Location;

import com.starredsolutions.weatherlib.engines.WundergroundEngine;

/**
 * @author juan
 *
 */
public class AndroidWeatherLibrary {

	/**
	 * 
	 * @param context
	 * @param apiKey
	 * @param callback
	 * @param countryName
	 * @param cityName
	 */
	public static void queryAsyncWunderground(Context context,String apiKey,AndroidWeatherCallback callback,String countryName,String cityName){
		new WundergroundEngine(context,apiKey,callback,countryName,cityName,false);
	}
	
	/**
	 * 
	 * @param context
	 * @param apiKey
	 * @param callback
	 * @param countryName
	 * @param cityName
	 * @param force
	 */
	public static void queryAsyncWunderground(Context context,String apiKey,AndroidWeatherCallback callback,String countryName,String cityName,boolean force){
		new WundergroundEngine(context,apiKey,callback,countryName,cityName,force);
	}
	
	/**
	 * 
	 * @param context
	 * @param apiKey
	 * @param callback
	 * @param location
	 */
	public static void queryAsyncWunderground(Context context,String apiKey,AndroidWeatherCallback callback,Location location){
		new WundergroundEngine(context,apiKey,callback,location,false);
	}
	
	/**
	 * 
	 * @param context
	 * @param apiKey
	 * @param callback
	 * @param location
	 * @param force
	 */
	public static void queryAsyncWunderground(Context context,String apiKey,AndroidWeatherCallback callback,Location location,boolean force){
		new WundergroundEngine(context,apiKey,callback,location,force);
	}
	
	public interface AndroidWeatherCallback{
		/**
		 * 
		 * @param weatherData
		 */
		void onWeatherData(final WeatherData weatherData);
	}

}
