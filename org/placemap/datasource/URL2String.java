
package org.placemap.datasource;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class URL2String {
    public static String get(String url) {

	HttpURLConnection connection = null;
	OutputStreamWriter wr;
	BufferedReader rd;
	StringBuilder sb;
	String line;
	String content = "";
    
	URL serverAddress;
    
	try {

	    //String encoding = "UTF-8";
	    //serverAddress = new URL(URLEncoder.encode(url,encoding));
	    serverAddress = new URL(url);
	    //set up out communications stuff
	    connection = null;
        
	    //Set up the initial connection
	    connection = (HttpURLConnection)serverAddress.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setDoOutput(true);
	    connection.setReadTimeout(10000);
                    
	    connection.connect();
        
	    //get the output stream writer and write the output to the server
	    //not needed in this example
	    //wr = new OutputStreamWriter(connection.getOutputStream());
	    //wr.write("");
	    //wr.flush();
        
	    //read the result from the server
	    rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    sb = new StringBuilder();
        
	    while ((line = rd.readLine()) != null)
		{
		    sb.append(line);
		}
	    content = sb.toString();

	    //System.out.println(sb.toString());

                    
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (ProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	finally
	    {
		//close the connection, set all objects to null
		connection.disconnect();
		rd = null;
		sb = null;
		wr = null;
		connection = null;
	    }

	return content;

    }

    

}