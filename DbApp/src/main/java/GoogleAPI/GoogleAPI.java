package GoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by MJPS on 23/05/2017.
 */
public class GoogleAPI {
	
	private final String LOG_TAG = "DbTestProject";
	
	private final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private final String GEOCODE_API_BASE = "https://maps.googleapis.com/maps/api";
	private final String STATIC_API_BASE = "https://maps.googleapis.com/maps/api";
	
	private final String TYPE_STATIC_MAP = "/staticmap?";
	private final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private final String TYPE_DETAILS = "/details";
	private final String TYPE_SEARCH = "/nearbysearch";
	private final String TYPE_RGEOCODE = "/geocode";
	private final String OUT_JSON = "/json?";
	
	private final String API_KEY = "AIzaSyCSrSediuHzqqIbZC5JUvAEzEjiP9FDd8c";
	
	/**
	 * @param lat, latitude
	 * @param lng, longitude
	 * @param zoom, the amount of detail needed:
	 *              1 = World
	 *              5 = Landmass/continent
	 *              10 = City
	 *              15 = Streets
	 *              20 = Buildings
	 * @param size, of the picture 640x640 is max for free.
	 * @return {@link URL}, picture url for later processing.
	 * */
	public URL getAndSavePictureLatLng(String lat, String lng, int zoom, int size) {
		
		URL picUrl = null;
		InputStream is;
		OutputStream os;
		
		try {
			
			StringBuilder sb = new StringBuilder(STATIC_API_BASE);
			sb.append(TYPE_STATIC_MAP);
			sb.append("center="+lat+","+lng);
			sb.append("&zoom="+String.valueOf(zoom));
			sb.append("&size="+size+"x"+size);
			sb.append("&key=" + API_KEY);
			
			picUrl = new URL(sb.toString());
			is = picUrl.openStream();
			os = new FileOutputStream(lat+" "+lng+".png");
			
			byte[] b = new byte[2048];
			int length;
			
			while ((length = is.read(b)) != -1) {
				os.write(b, 0,length);
			}
			
			is.close();
			os.close();
		} catch (IOException e) {
			System.out.println("Error processing URL");
			System.out.printf(e.toString());
		}
		
		return picUrl;
	}
	
	public ArrayList<Place> reverseGeocode(String lat, String lng) {
		ArrayList<Place> resultList = null;
		
		HttpURLConnection connection = null;
		StringBuilder results = new StringBuilder();
		
		
		try {
			
			StringBuilder sb = new StringBuilder(GEOCODE_API_BASE);
			sb.append(TYPE_RGEOCODE);
			sb.append(OUT_JSON);
			sb.append("&latlng=" + lat + "," + lng);
			sb.append("&key=" + API_KEY);
			
			URL url = new URL(sb.toString());
			connection = (HttpURLConnection) url.openConnection();
			
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				results.append(buff, 0, read);
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		} catch (MalformedURLException e) {
			System.out.println("Error processing places API URL");
			System.out.println(e);
		} catch (IOException e) {
			System.out.println("Error Connection to Places API");
			System.out.println(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		Place place;
		try {
			//Creating JSON object hierarchy
			JSONObject jsObject = new JSONObject(results.toString());
			JSONArray resultsArr = jsObject.getJSONArray("results");
			
			//extract the Place Descriptions
			resultList = new ArrayList<>(resultsArr.length());
			
			for (int i = 0; i < resultsArr.length(); i++) {
				place = new Place();
				place.setFormattedAddress(resultsArr.getJSONObject(i).getString("formatted_address"));
				place.setReference(resultsArr.getJSONObject(i).getString("place_id"));
				resultList.add(place);
			}
		} catch (JSONException e) {
			System.out.println("Error processing JSON results");
			System.out.println(e);
		}
		
		
		return resultList;
		
	}
	
	public ArrayList<Place> search(String keyword, String lat, String lng, int radius) {
		ArrayList<Place> resultList = null;
		
		HttpURLConnection connection = null;
		StringBuilder results = new StringBuilder();
		
		
		try {
			
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append(TYPE_SEARCH);
			sb.append(OUT_JSON);
			sb.append("&location=" + lat + "," + lng);
			sb.append("&radius=" + String.valueOf(radius));
			sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
			sb.append("&key=" + API_KEY);
			
			URL url = new URL(sb.toString());
			connection = (HttpURLConnection) url.openConnection();
			
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				results.append(buff, 0, read);
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		} catch (MalformedURLException e) {
			System.out.println("Error processing places API URL");
			System.out.println(e);
		} catch (IOException e) {
			System.out.println("Error Connection to Places API");
			System.out.println(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		
		try {
			//Creating JSON object hierarchy
			JSONObject jsObject = new JSONObject(results.toString());
			JSONArray resultsArr = jsObject.getJSONArray("results");
			
			//extract the Place Descriptions
			resultList = new ArrayList<>(resultsArr.length());
			
			for (int i = 0; i < resultsArr.length(); i++) {
				Place place = new Place();
				place.setReference(resultsArr.getJSONObject(i).getString("reference"));
				place.setName(resultsArr.getJSONObject(i).getString("name"));
				resultList.add(place);
			}
		} catch (JSONException e) {
			System.out.println("Error processing JSON results");
			System.out.println(e);
		}
		
		
		return resultList;
		
	}
	
	public Place details(String reference) {
		HttpURLConnection connection = null;
		StringBuilder results = new StringBuilder();
		
		
		try {
			
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append(TYPE_DETAILS);
			sb.append(OUT_JSON);
			sb.append("?sensor=false");
			sb.append("&key=" + API_KEY);
			sb.append("&reference=" + URLEncoder.encode(reference, "utf8"));
			
			URL url = new URL(sb.toString());
			connection = (HttpURLConnection) url.openConnection();
			
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				results.append(buff, 0, read);
			}
			
		} catch (UnsupportedEncodingException e) {
			System.out.println();
		} catch (MalformedURLException e) {
			System.out.println("Error processing places API URL");
			System.out.println(e);
		} catch (IOException e) {
			System.out.println("Error Connection to Places API");
			System.out.println(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		Place place = null;
		
		try {
			//Creating JSON object hierarchy
			JSONObject jsObject = new JSONObject(results.toString()).getJSONObject("result");
			//extract data
			place = new Place();
			place.setName(jsObject.getString("name"));
			place.setFormattedAddress(jsObject.getString("formatted_address"));
			
		} catch (JSONException e) {
			System.out.println("Error processing JSON results");
			System.out.println(e);
		}
		
		return place;
		
	}
	
}
