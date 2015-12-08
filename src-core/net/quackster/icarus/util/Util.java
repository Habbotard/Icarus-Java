package net.quackster.icarus.util;

import java.io.File;
import java.security.SecureRandom;

import net.quackster.icarus.pooling.ThreadPooling;

public class Util {

	private Configuration configuration;
	private ThreadPooling threadPooling;
	private SecureRandom secureRandom;

	public Util() {
		this.configuration = new Configuration(new File("icarus.properties"));
		this.threadPooling = new ThreadPooling();
		this.secureRandom = new SecureRandom();
	}
	
	public boolean isNullOrEmpty(String param) { 
	    return param == null || param.trim().length() == 0;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public ThreadPooling getThreadPooling() {
		return threadPooling;
	}

	public SecureRandom getRandom() {
		return secureRandom;
	}
}
