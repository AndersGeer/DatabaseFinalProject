package PictureService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by MJPS on 23/05/2017.
 */
public class PictureService implements IService{
	
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
	
	
	private String lat;
	private String lng;
	
	public String getLat() {
		return lat;
	}
	
	public String getLng() {
		return lng;
	}
	
	private void setLat(String lat) {
		this.lat = lat;
	}
	
	private void setLng(String lng) {
		this.lng = lng;
	}
	
	//Dependency injection for constructor
	private StringBuilder sb;
	private InputStream is;
	private InputStreamReader in;
	private OutputStream os;
	private HttpURLConnection connection;
	
	//Dependency injection Setter
	private URL url;
	
	public void setUrl(String url) throws MalformedURLException {
		if (url == null) {
			throw new InvalidParameterException("url must not be null");
		}
		this.url = new URL(url);
	}
	public URL getUrl(){return url;}
	
	
	
	/**
	 * Constructor for getPictureFromLatLng
	 * @param sb, Stringbuilder that will append Strings (eg. https://maps.googleapis.com/maps...)
	 * @param is, Input stream for reading from the URL connection.
	 * @param os, Creates a file output stream to write to the file with the
	 * @param lat, latitude
	 * @param lng, longitude
	 * specified name
	 *
	 * */
	public PictureService(StringBuilder sb, InputStream is, OutputStream os, String lat, String lng) throws FileNotFoundException {
		this.sb = sb;
		this.is = is;
		
		if ((lat.length() != 0 || lat != "") && ( lng.length() != 0  || lng != "")) {
			setLat(lat);
			setLng(lng);
			this.os = new FileOutputStream(lat+" "+lng+".png");
		} else {
			throw new FileNotFoundException("cannot create file, if lat and lng is null/empty");
		}
		
	}
	
	/**
	 * @param zoom, the amount of detail needed:
	 *              1 = World
	 *              5 = Landmass/continent
	 *              10 = City
	 *              15 = Streets
	 *              20 = Buildings
	 * @param size, of the picture 640x640 is max for free.
	 * @return {@link URL}, picture url for later processing.
	 * */
	public URL getPicture(int zoom, int size) {
		
		
		try {
			
			sb.append(STATIC_API_BASE);
			sb.append(TYPE_STATIC_MAP);
			sb.append("center="+getLat()+","+getLng());
			sb.append("&zoom="+String.valueOf(zoom));
			sb.append("&size="+size+"x"+size);
			sb.append("&key=" + API_KEY);
			
			setUrl(sb.toString());
			is = getUrl().openStream();
			
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
		
		return url;
	}
	
	
	/**
	 * Constructor for reverseGeocode
	 * @param sb, Stringbuilder that will append Strings (eg. https://maps.googleapis.com/maps...)
	 * @param connection, instance is used to make a single request
	 * @param in, an input stream that reads from an open connection
	 *
	 * */
	public PictureService(StringBuilder sb, HttpURLConnection connection, InputStreamReader in) {
		this.sb = sb;
		this.connection = connection;
		this.in = in;
	}
		
	/**
	 * Returns an arraylist of places that is based on latitude and longitude and reverse geocode it
	 * @param lat, latitude
	 * @param lng, longitude
	 *
	 * @return {@link ArrayList<Place>}
	 * */
	public ArrayList<Place> reverseGeocode(String lat, String lng) {
		
		//a seperate stringbuilder to hold values from json
		StringBuilder results = new StringBuilder();
		
		
		try {
			
			sb.append(GEOCODE_API_BASE);
			sb.append(TYPE_RGEOCODE);
			sb.append(OUT_JSON);
			sb.append("&latlng=" + lat + "," + lng);
			sb.append("&key=" + API_KEY);
			
			setUrl(sb.toString());
			connection = (HttpURLConnection) getUrl().openConnection();
			
			in = new InputStreamReader(connection.getInputStream());
			
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
		
		ArrayList<Place> resultList = null;
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
		HttpURLConnection connection = null;
		StringBuilder results = new StringBuilder();
		
		
		try {
			
			sb.append(PLACES_API_BASE);
			sb.append(TYPE_SEARCH);
			sb.append(OUT_JSON);
			sb.append("&location=" + lat + "," + lng);
			sb.append("&radius=" + String.valueOf(radius));
			sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
			sb.append("&key=" + API_KEY);
			
			setUrl(sb.toString());
			connection = (HttpURLConnection) url.openConnection();
			
			in = new InputStreamReader(connection.getInputStream());
			
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
		
		ArrayList<Place> resultList = null;
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
		
		StringBuilder results = new StringBuilder();
		try {
			
			sb.append(PLACES_API_BASE);
			sb.append(TYPE_DETAILS);
			sb.append(OUT_JSON);
			sb.append("?sensor=false");
			sb.append("&key=" + API_KEY);
			sb.append("&reference=" + URLEncoder.encode(reference, "utf8"));
			
			setUrl(sb.toString());
			connection = (HttpURLConnection) url.openConnection();
			
			in = new InputStreamReader(connection.getInputStream());
			
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
