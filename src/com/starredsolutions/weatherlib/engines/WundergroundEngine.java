/**
 * 
 */
package com.starredsolutions.weatherlib.engines;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.starredsolutions.weatherlib.AndroidWeatherLibrary.AndroidWeatherCallback;
import com.starredsolutions.weatherlib.Forecast;
import com.starredsolutions.weatherlib.WeatherData;

/**
 * @author juan
 *
 */
public class WundergroundEngine {
	private final static String TAG = "WundergroundEngine"; 
	private final static String URLCity = "http://api.wunderground.com/api/%s/conditions/forecast/astronomy/lang:SP/q/%s/%s.json";
	private final static String URLGeo = "http://api.wunderground.com/api/%s/conditions/forecast/astronomy/lang:SP/q/%f,%f.json";
	AQuery aq;
	WeatherData mWeatherData;
	Handler mHandler;
	AndroidWeatherCallback mCallback;
	Context mContext;
	/**
	 * 
	 * @param context
	 * @param apiKey
	 * @param callback
	 * @param countryName
	 * @param cityName
	 */
	public WundergroundEngine(Context context,String apiKey,AndroidWeatherCallback callback,String countryName, String cityName,boolean force) {
		if(callback == null){
			throw new IllegalArgumentException("callback is null");
		}
		mContext = context;
		aq = new AQuery(context);
		mCallback = callback;
		
		mHandler = new Handler();
		update(String.format(URLCity, apiKey,countryName,cityName.replaceAll(" ", "_")),force);
	}
	
	/**
	 * 
	 * @param context
	 * @param apiKey
	 * @param callback
	 * @param location
	 */
	public WundergroundEngine(Context context,String apiKey,AndroidWeatherCallback callback,Location location,boolean force) {
		if(callback == null){
			throw new IllegalArgumentException("callback is null");
		}
		
		if(location == null){
			throw new IllegalArgumentException("location is null");
		}
		mContext = context;
		aq = new AQuery(context);
		mCallback = callback;
		
		mHandler = new Handler();
		//update(String.format(URLGeo, apiKey,location.getLatitude(),location.getLongitude()),force);
		update("http://cuentaporotos.com.ar/img/sample.json",force);
	}
	
	private void update(final String queryUrl,boolean force){
		Log.d(TAG,queryUrl);
		long expire = 240 * 60 * 1000;
		if(force){
			expire = -1;
		}
		aq.ajax(queryUrl,JSONObject.class,expire,new MyAjaxCallback());
	}
	
	
	class MyAjaxCallback extends AjaxCallback<JSONObject>{
		
		@Override
        public void callback(String url, JSONObject json, AjaxStatus status){
			if(json != null){
				mWeatherData = new WeatherData();
				
				try {
					if(json.has("current_observation")){
						JSONObject currentObservation = json.getJSONObject("current_observation"); 
						if(currentObservation.has("display_location")){
							mWeatherData.locationName = currentObservation.getJSONObject("display_location").getString("full");
						}
						mWeatherData.timeStamp 			= currentObservation.getLong("local_epoch");
						mWeatherData.temperature 		= Double.valueOf(currentObservation.getDouble("temp_c")).floatValue();
						mWeatherData.weather 			= currentObservation.getString("weather");
						mWeatherData.relativeHumidity 	= currentObservation.getString("relative_humidity");
						mWeatherData.windDirection 		= currentObservation.getString("wind_dir");
						mWeatherData.windSpeed 			= Double.valueOf(currentObservation.getDouble("wind_kph")).floatValue();
						mWeatherData.pressure 			= Double.valueOf(currentObservation.getDouble("pressure_mb")).floatValue();
						mWeatherData.feelslike 			= Double.valueOf(currentObservation.getDouble("feelslike_c")).floatValue();
						mWeatherData.visibility			= Double.valueOf(currentObservation.getDouble("visibility_km")).floatValue();
						mWeatherData.iconName 			= currentObservation.getString("icon");
						mWeatherData.iconUrl 			= currentObservation.getString("icon_url");
					}
					
					if(json.has("forecast")){
						mWeatherData.forecast = new Forecast[4];
						JSONObject forecast = json.getJSONObject("forecast");
						if(forecast.has("txt_forecast")){
							JSONArray textForecast = forecast.getJSONObject("txt_forecast").getJSONArray("forecastday");
							JSONArray simpleForecast = forecast.getJSONObject("simpleforecast").getJSONArray("forecastday");
							JSONObject textFcastDay = null;
							JSONObject simpleFcastDay = null;
							for(short i = 0, j = 0; i < textForecast.length(); i +=2, j++){
								textFcastDay = textForecast.getJSONObject(i);
								mWeatherData.forecast[j] = new Forecast();
								mWeatherData.forecast[j].description = textFcastDay.getString("fcttext_metric");
								mWeatherData.forecast[j].day = textFcastDay.getString("title");
								
								textFcastDay = textForecast.getJSONObject(i+1);
								mWeatherData.forecast[j].description +=  " | " + textFcastDay.getString("fcttext_metric");
								
								simpleFcastDay = simpleForecast.getJSONObject(j);
								mWeatherData.forecast[j].temperatureHigh = Double.valueOf(simpleFcastDay.getJSONObject("high").getDouble("celsius")).floatValue();
								mWeatherData.forecast[j].temperatureLow  = Double.valueOf(simpleFcastDay.getJSONObject("low").getDouble("celsius")).floatValue();
							}
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mCallback.onWeatherData(mWeatherData);
					}
				});
				
			}else{
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mCallback.onWeatherData(null);
					}
				});
			}
		}
	}

}
