package org.meruvian.midas.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

public class GlobalVariable {
	public static final String RESOURCE_BUNDLE_FILENAME = "midas.properties";

	public static final String getGlobalVariableByKey(Context ctx, String key) {
		Properties configFile = new Properties();
		try {
			InputStream inputStream = ctx.getAssets().open(
					RESOURCE_BUNDLE_FILENAME);
			configFile.load(inputStream);
		} catch (IOException e) {
			System.err.println("Failed to open microlog property file");
			e.printStackTrace();
		}
		return configFile.getProperty(key, "");
	}
}
