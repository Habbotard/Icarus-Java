package net.quackster.icarus.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.quackster.icarus.log.Log;

public class Configuration {

	private Properties config;
	
	public Configuration(FileInputStream stream) {
		
		this.config = new Properties();
		
		try {
			this.config.load(stream);
		} catch (IOException e) {
			Log.exception(e);
		}
	}
	
	public String get(String key) {
		return config.getProperty(key);
	}
	
	public int getInteger(String key) {
		return Integer.parseInt(config.getProperty(key));
	}
	
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(config.getProperty(key));
	}
}
