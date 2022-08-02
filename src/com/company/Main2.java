package com.company;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import javax.swing.JOptionPane;

import org.json.JSONObject;


/* This would be needed to be added if used into a maven project
 * the dependency allows for the parsing of JSON data
 	<!-- https://mvnrepository.com/artifact/org.json/json -->
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20220320</version>
</dependency>

 * */
public class Main2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Method 2 is using java.net.http.HttpClient
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:3000/profile")).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenAccept(System.out::println);
		//.join(); 
		/*
		 * join is commented out since it will print the request from the url
		 	kept out to check if parsing of JSON is correct
		 * */
		
		/*
		 * I wanted to find a way to not have this next line print out what was received from 
		 * the local host, but this was the way I had a working solution to parse
		 * through the JSON text to select each key and print to GUI
		 * */
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		//HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
		  // System.out.println(response.statusCode());
		   //System.out.println(response.body()); 
		   
		   JSONObject test = new JSONObject(response.body().toString());
		   //System.out.println("HERE>>>>>>>: " +response.body().toString());
		   System.out.println("Username:" + test.getString("username"));
		   System.out.println("Name: " + test.getString("name"));
		   
		   /*After receiving "username" and "name" key, a new JSONObject 
		    * needed to be created since street1, street2, city, state, and zip-code
		    * were nested under address
		    * 
		    * The next lines parsed normally, but an if statement was used to parser pass the null value
		    * value of "street2"
		    * */
		    
		   JSONObject test2 = (JSONObject)test.get("address");
		   System.out.println("street1: " +test2.getString("street1"));
		   if(test2.isNull("street2")){
		   System.out.println("street2: null");
		   }else {
			   System.out.println("street2: " +test2.getString("street2"));
		   }
		   System.out.println("city: " +test2.getString("city"));
		   System.out.println("state: " +test2.getString("state"));
		   System.out.println("zip-code: " +test2.getInt("zip-code"));
		   
		   /*Attempted to print the information on anything other than a console.
		    * Console printed from line 44 and all sout(s) lines following
		    * GUI was able to print out the same strings with no issues from JSONObject(s)
		    * 
		    * */
		   
		   JOptionPane.showMessageDialog(null, "Username: " + test.getString("username")+ "\n" 
		    + "name: "+ test.getString("name") + "\nadresss:\n  street1: " + test2.getString("street1")
		    + "\n  street2: null\n  city: " + test2.getString("city") + "\n  state: " + test2.getString("state")
		    + "\n  zip-code: " + test2.getString("zip-code"));
		//System.out.println("TESTING TO SEE SOMETHING");
		//JSONObject test = new JSONObject(request.toString());
		//System.out.println("name:> " +test.getString("name"));
		
		

	}
	

}