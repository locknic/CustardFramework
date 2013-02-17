package net.lonestranger.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {
	
	Properties properties;
	String filename;
	
	public SettingsManager() {
		init();
	}
	
	public SettingsManager(String file) {
		init(file);
	}

	public void init(String file)
	{
		properties = new Properties();
		filename = file; 
				
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			properties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file);
		} catch (IOException e) {
			System.out.println("Unknown SettingsManager error: " + e.toString());
		}
			
	}
	
	public void init()
	{
		init("game.properties");
	}
	
	public String getValue(String key)
	{
		return properties.getProperty(key);
	}
	
	public void setValue(String key, String value)
	{
		properties.setProperty(key, value);
		
	}
	
	
}
