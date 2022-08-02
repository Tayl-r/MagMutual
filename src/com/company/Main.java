package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	private static HttpURLConnection connection;
	public static void main(String[] args) {
		//Method 1 on how to retrieve info from local host
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		
		try {
			URL url = new URL("http://localhost:3000/profile");
			connection = (HttpURLConnection) url.openConnection();
			
			//Request Setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			//If "status" = 200, then the connection to local host was a SUCCESS!!!
			//System.out.println(status);
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connection.disconnect();
		}

	}
	

}
