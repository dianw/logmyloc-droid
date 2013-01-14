package org.meruvian.midas.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUtils {
	private static final Logger LOG = LoggerFactory.getLogger(RestUtils.class);

	private static Map<String, HttpRequestBase> requests = new HashMap<String, HttpRequestBase>();
	static {
		requests.put("get", new HttpGet());
		requests.put("post", new HttpPost());
		requests.put("put", new HttpPut());
		requests.put("delete", new HttpDelete());
		requests.put("option", new HttpOptions());
		requests.put("head", new HttpHead());
	}

	public static HttpResponse request(String method, String uri,
			NameValuePair... nameValuePairs) {
		HttpRequestBase request = requests.get(method.toLowerCase(Locale.US));
		List<NameValuePair> valuePairs = Arrays.asList(nameValuePairs);

		if (request instanceof HttpEntityEnclosingRequestBase) {
			try {
				HttpEntityEnclosingRequestBase r = (HttpEntityEnclosingRequestBase) request;
				r.setEntity(new UrlEncodedFormEntity(valuePairs));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
			}
		} else {
			if (!uri.endsWith("?"))
				uri += "?";
			uri += URLEncodedUtils.format(valuePairs, "utf-8");
		}

		try {
			request.setURI(new URI(uri));
		} catch (URISyntaxException e) {
			LOG.error(e.getMessage(), e);
		}

		return buildResponseFromRequest(request);
	}

	private static HttpResponse buildResponseFromRequest(HttpRequestBase request) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			return httpClient.execute(request);
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}
}
