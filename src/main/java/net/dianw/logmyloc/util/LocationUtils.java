package net.dianw.logmyloc.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationUtils {
	private LocationUtils() {
	}

	public static Location currentLocation(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		String provider = locationManager.getBestProvider(criteria, true);

		return locationManager.getLastKnownLocation(provider);
	}

	public static Location currentLocation(Context context,
			LocationListener listener) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		String provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 0, 0, listener);

		return locationManager.getLastKnownLocation(provider);
	}
}
