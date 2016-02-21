package api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class GoogleGeocoding {
	public GoogleGeocoding(){

	}
	
	final private static String ApiKey = "AIzaSyAQU7SHznT4VRvZFf-wYVFSpqRU83S3ddQ";
	final private static String Uri = "https://maps.googleapis.com/maps/api/geocode/json?";
	private static JsonObject JsonObj;
	
	private static void setJsonObject(String queryString){

		try{
			URLConnection apiConnection = new URL(queryString).openConnection();
			JsonReader jReader = new JsonReader(new InputStreamReader(apiConnection.getInputStream()));
			Gson gsonBuilder = new GsonBuilder().create();
			
			JsonElement jElem = gsonBuilder.fromJson(jReader,JsonElement.class);
			JsonObj = jElem.getAsJsonObject();
		}
		catch(Exception ex){
			System.out.println("Json Request Error!");
			return;
		}
	}
	
	private static String createQueryString(String id1, String arg1){
		if(arg1 == null || id1 == null)
			return "Error";
		
		String query = Uri + id1 + "=" + formatRequestAddress(arg1);
		query += "&key=" + ApiKey;
		
		return query;
	}
	
	public static String[] getGeocoordinatesFromAddress(String address){
		String fullAddress = address;
		if(!address.toLowerCase().contains("philadelphia"))
			fullAddress = address + ", Philadelphia, PA"; //"3710 LANKENAU RD"
		
		String queryString = createQueryString("address",address);
		
		// results.geometry.location.lat	results.geometry.location.lng
		setJsonObject(queryString);
		JsonArray jArray = (JsonArray)JsonObj.getAsJsonArray("results");
		JsonObj = jArray.get(0).getAsJsonObject();
		
		JsonObject jElem = JsonObj.get("geometry").getAsJsonObject();
		JsonObj= jElem.get("location").getAsJsonObject();//get("location").
		
		String[] geoCoords = {"",""};
		geoCoords[0] = JsonObj.get("lat").toString();//("results");
		geoCoords[1] = JsonObj.get("lng").toString();//("results");
		
		return geoCoords;
	}
	private static String formatRequestAddress(String address){
		return address.replace(" ", "+");
	}
	
	
	// Method below was used to test the
	// Json parsing outside of android app
	// due to Api Key limitations
	/*public static String testJson;
	public static void ReadTestJson(){	
		try{
			BufferedReader br = new BufferedReader(new FileReader("Assets/googlecoordsJson.txt"));
			testJson = "";
			while(br.ready()){
			
				String line = br.readLine();
				testJson+= line;
			}
		}
		catch(Exception e){
		
		}
		Gson gsonBuilder = new GsonBuilder().create();
		JsonElement jElem = gsonBuilder.fromJson(testJson, JsonElement.class);
		JsonObj = jElem.getAsJsonObject();
	}
*/
}
