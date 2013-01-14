package net.dianw.logmyloc.service;

import net.dianw.logmyloc.content.model.Place;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.meruvian.midas.util.JsonUtils;

import android.location.Location;
import android.os.Bundle;

public class NominatimRestService extends MapLoaderService {
	public static final String OSM_NOMINATIM_URL = "http://nominatim.openstreetmap.org/reverse";

	@Override
	public void onLocationChanged(Location location) {
		NameValuePair[] nameValuePairs = {
				new BasicNameValuePair("lat", Double.toString(location
						.getLatitude())),
				new BasicNameValuePair("lon", Double.toString(location
						.getLongitude())),
				new BasicNameValuePair("format", "json") };

		Place place = JsonUtils.requestJsonFromHttp(Place.class, "get",
				OSM_NOMINATIM_URL, nameValuePairs);

		Bundle bundle = new Bundle();
		bundle.putSerializable("place", place);
		location.setExtras(bundle);

		super.onLocationChanged(location);
	}
}