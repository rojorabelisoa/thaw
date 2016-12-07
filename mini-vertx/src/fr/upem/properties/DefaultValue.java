package fr.upem.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultValue {
	private final static String databaseName = "testDB.db";
	InputStream inputStream ;

	public static String getDatabasename() {
		return databaseName;
	}
	public String getDatabaseValues() throws IOException {
		String db ="";
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			db = prop.getProperty("testDB");
		} finally {
			inputStream.close();
		}
		return db;
	}
	
}
