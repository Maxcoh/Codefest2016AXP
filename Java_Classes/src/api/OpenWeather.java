package api;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class OpenWeather {
	public OpenWeather(){
		
	}
	
	// Instance Variables
	final private String ApiKey = "b5f23ca40514412485b51ebd8cf05b09";
	final private String Uri = "http://api.openweathermap.org/data/2.5/weather?";
	private JsonObject jsonObj;
	
	// Example query: http://api.openweathermap.org/data/2.5/weather ?lat=35 &lon=139 &appid=44db6a862fba0b067b1930da0d769e98
	// Parameters
	private String CityName; 			// q 
	private String CityId;				// id
	private String[] GeoCoordinates; 	// lat, lon
	private String ZipCode;				// Zip
	
	// Public Methods
	// Setters
	public void setCityName(String cityName){
		CityName = cityName;
	}
	
	public void setCityId(String cityId){
		CityId = cityId;
	}
	public void setGeocoordinates(String lat, String lon){
		GeoCoordinates = new String[]{lat, lon};
	}
	public void SetZipCode(String zipCode){
		ZipCode = zipCode;
	}
	
	// Getters
	public String getCityName(){
		return CityName;
	}
	
	public String getCityId(){
		return CityId;
	}
	
	public String[] getGeoCoordinates(String iat, String ion){
		return GeoCoordinates;
	}
	
	public String getZipCode(String zipCode){
		return ZipCode;
	}
	
	// Api call methods
	public void queryWeatherByCityName(){
		String queryString = createQueryString("q",CityName);
		setJsonObject(queryString);
	}
	
	public void queryWeatherByCityName(String cityName){
		setCityName(cityName);
		queryWeatherByCityName();
	}
	
	public void queryWeatherByGeoCoordinates(){
		String queryString = createQueryString("lat",GeoCoordinates[0],"lon",GeoCoordinates[1]);
		setJsonObject(queryString);
	}
	
	public void queryWeatherByGeoCoordinates(String Lat, String Lon ){
		setGeocoordinates(Lat,Lon);
		queryWeatherByGeoCoordinates();
	}

	public void queryWeatherByGeoCoordinates(double Lat, double Lon ){
		setGeocoordinates(Double.toString(Lat), Double.toString(Lon));
		queryWeatherByGeoCoordinates();
	}
	
	private void setJsonObject(String queryString){

		try{
			URLConnection apiConnection = new URL(queryString).openConnection();
			JsonReader jReader = new JsonReader(new InputStreamReader(apiConnection.getInputStream()));
			Gson gsonBuilder = new GsonBuilder().create();
			
			JsonElement jElem = gsonBuilder.fromJson(jReader,JsonElement.class);
			jsonObj = jElem.getAsJsonObject();
		}
		catch(Exception ex){
			System.out.println("Json Request Error!");
			return;
		}
	}
	
	// Weather detail methods
	public String getHumidity(){
		try{
			return jsonObj.getAsJsonObject("main").get("humidity").getAsString();
		}
		catch(Exception e){
			System.out.print("No Humidity Data Found");
			return "No Data Found";
		}
	}
	
	public String getPressure(){
		try{
			return jsonObj.getAsJsonObject("main").get("pressure").getAsString();
		}
		catch(Exception e){
			System.out.print("No Pressure Data Found");
			return "No Data Found";
		}
	}
	
	public String getRain(){
		try{
			return jsonObj.getAsJsonObject("rain").get("3h").getAsString();
		}
		catch(Exception e){
			System.out.print("No Rain Data Found");
			return "No Data Found";
		}
	}
	
	public String getClouds(){
		try{
			return jsonObj.getAsJsonObject("clouds").get("all").getAsString();
		}
		catch(Exception e){
			System.out.print("No Cloud Data Found");
			return "No Data Found";
		}
	}
		
	// Private Methods
	private String createQueryString(String id1, String arg1, String id2, String arg2){
		if(arg1 == null || id1 == null)
			return "Error";
		String query = Uri + id1 + "=" + arg1;
		if(arg2 != null && id2 != null)
			query += "&" + id2 + "=" + arg2;
		
		query += "&appid=" + ApiKey;
		
		return query;
	}
	
	private String createQueryString(String arg1, String id1){
		return createQueryString(arg1, id1, null, null);
		
	}
}
