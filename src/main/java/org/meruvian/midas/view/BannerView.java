package org.meruvian.midas.view;

import net.dianw.logmyloc.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

public class BannerView extends WebView {

	private String zoneId = "";
	private String serverHost = "";
	private int refreshInterval = 0;

	public BannerView(Context context) {
		super(context);
		initAds();
	}

	public BannerView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		initAds(attributeSet, context);
	}

	private void initAds(AttributeSet attributeSet, Context context) {
		TypedArray a = context.obtainStyledAttributes(attributeSet,
				R.styleable.BannerView);
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.BannerView_serverHost:
				setServerHost(a.getString(attr));
				Log.d(BannerView.class.getSimpleName(),
						"found attribute: " + a.getString(attr));
				break;
			case R.styleable.BannerView_zoneId:
				setZoneId(a.getString(attr));
				Log.d(BannerView.class.getSimpleName(),
						"found attribute: " + a.getString(attr));
				break;
			case R.styleable.BannerView_refreshInterval:
				setRefreshInterval(a.getInteger(attr, 5));
				Log.d(BannerView.class.getSimpleName(),
						"found attribute: " + a.getString(attr));
				break;
			}
		}
		initAds();
		a.recycle();
	}

	private void initAds() {
		if (getServerHost().equals("")) {
			Log.e(BannerView.class.getSimpleName(),
					"You must specify serverHost attribute");
		} else if (getZoneId().equals("")) {
			Log.e(BannerView.class.getSimpleName(),
					"You must specify zoneid attribute");
		} else {
			setVerticalScrollBarEnabled(false);
			setHorizontalScrollBarEnabled(false);
			getSettings().setJavaScriptEnabled(true);
			addJavascriptInterface(new BannerData(getZoneId(), getServerHost(),
					getRefreshInterval()), "bannerdata");
			loadUrl("file:///android_asset/invocationads.html");
		}
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(final String zoneId) {
		this.zoneId = zoneId;
	}

	public void setZoneId(final int zoneId) {
		setZoneId(Integer.toString(zoneId));
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		StringBuilder baseURLBuilder = new StringBuilder(
				serverHost.length() + 8);
		baseURLBuilder.append("http://");
		baseURLBuilder.append(serverHost);
		baseURLBuilder.append('/');
		this.serverHost = baseURLBuilder.toString();
	}

	public class BannerData {
		private String zoneId = "";
		private String serverHost = "";
		private int refreshInterval = 0;

		public BannerData(String zoneId, String serverHost, int refreshInterval) {
			this.zoneId = zoneId;
			this.serverHost = serverHost;
			this.refreshInterval = refreshInterval;
			Log.d(BannerView.class.getSimpleName(), "bannerdata: "
					+ this.serverHost + ", " + this.zoneId + ", "
					+ this.refreshInterval);
		}

		public String getBannerData() throws JSONException {
			JSONObject json = new JSONObject();
			json.put("serverHost", this.serverHost);
			json.put("zoneid", this.zoneId);
			json.put("refreshInterval", this.refreshInterval);
			return json.toString();
		}
	}
}
