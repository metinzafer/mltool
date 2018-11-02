/**
 * 
 */
package com.gnsmind.springBoot.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gnsmind.springBoot.constant.Constants;

/**
 * @author NET
 *
 */
public class GetProperties {
	
	final static Logger logger = Logger.getLogger(GetProperties.class);
	
	public static Map<String, String> getProp(){
		
		Properties prop = new Properties();
		InputStream input = null;
		
		String filename = Constants.CONFIG_FILE;
		Map<String, String> propMap = new HashMap<String, String>();
		
		try {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			input = classLoader.getResourceAsStream(filename);
			
			if(input==null){
				
				throw new java.lang.RuntimeException("Sorry, unable to find " + filename);
			}
			
			prop.load(input);
			
			propMap.put("activateDB", prop.getProperty("activateDB"));
			propMap.put("dbType", prop.getProperty("dbType"));
			
		} catch (IOException ex) {
			
			throw new java.lang.RuntimeException("<GetProperties> - IO Error : " + ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					
					throw new java.lang.RuntimeException("<GetProperties> - File Closing Error : " + e.getMessage());
				}
			}
		}
		return propMap;
	}
	
}

