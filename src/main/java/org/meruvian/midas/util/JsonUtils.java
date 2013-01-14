package org.meruvian.midas.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class JsonUtils {
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

	public static <T> T requestJsonFromHttp(Class<T> clazz, String method,
			String uri, NameValuePair... nameValuePairs) {
		try {
			HttpEntity entity = RestUtils.request(method, uri, nameValuePairs)
					.getEntity();
			InputStream stream = entity.getContent();

			Gson gson = new Gson();
			return gson.fromJson(new InputStreamReader(stream), clazz);
		} catch (IllegalStateException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}
}
