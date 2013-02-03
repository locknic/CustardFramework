package net.lonestranger.network;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class Report {

	// URL to the target server that will accept the post
	// and translate it or whatever into a database.
	URL targetURL = null;

	// stores the data to be sent to the home server
	String dataStore;

	
	public Report() throws MalformedURLException
	{
		targetURL = new URL("http://localhost/store.php");
		dataStore = "test";
	}
	
	// obviously, this is very simple and requires putting 
	// into a blob elsewhere.  it could be updated for data
	// pairs later.
	public void setData(String data) {
		dataStore = data;
	}
	
	private String encodeData(String data) {
		// this is where the encryption would happen.
		return rot13(data);
	}
	
	private String decodeData(String data) {
		// this is where the decryption would happen.
		return rot13(data);
	}
	
	public String getData()
	{
		return dataStore;
	}

	
	
	private final String asc_shift(String string, int amount) {
		  char key = string.charAt(0);
		  if(string.length()==1) {
		    return String.valueOf((int)key + amount);
		  } else {
		    return string.valueOf((int)key + amount) + asc_shift(string.substring(1, string.length() - 1), amount);
		  }
	}
	
	private final String rot13(String s)
	{
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'A' && c <= 'Z') c -= 13;
            temp.append(c);
        }
        return temp.toString();	
	}
	
	
	public void setURL(String url) throws MalformedURLException
	{
		targetURL = new URL(url);
	}
	
	public String getURL()
	{
		return targetURL.getPath();
	}
	
	
	public void phoneHome()
	{
		// code snatched from http://www.exampledepot.com/egs/java.net/Post.html
		try {
			
			String encoded = encodeData(dataStore);
			
	        // Construct data
	        String data = URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(encoded, "UTF-8");
	    
	        // Send data
	        URLConnection conn = targetURL.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	    
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null) {
	            System.out.println("Response: " + line);
	        }
	        wr.close();
	        rd.close();
	    } 
		catch (FileNotFoundException e) {
	    	System.out.println("Target script not found: " + targetURL.toString());
	    } 
	    catch (UnknownHostException e) {
	    	System.out.println("Target host not found: " + targetURL.toString());	
	    	System.out.println("Possibly no internet connection?  Data will not be recorded on remote server.");		    	
	    }
		catch (Exception e)
	    {
	    	System.out.println("Unknown Report error: " + e.toString());
	    }

	}


	
	
}
