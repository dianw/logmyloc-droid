package net.dianw.logmyloc.service;

import net.dianw.logmyloc.constant.LocationConnectionStatus;

import org.meruvian.midas.util.ConnectionUtil;

import roboguice.service.RoboIntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.ResultReceiver;

public class MapLoaderService extends RoboIntentService implements
		LocationListener, LocationConnectionStatus {

	protected ResultReceiver receiver;

	public MapLoaderService() {
		super("");
	}

	public MapLoaderService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		receiver = intent.getParcelableExtra("receiver");
		receiver.send(CONNECTION_STARTED, null);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		String provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 0, 0, this);

		Location location = locationManager.getLastKnownLocation(provider);

		if (location == null) {
			onProviderDisabled(provider);
		} else {
			onLocationChanged(location);
		}

		if (!ConnectionUtil.isInternetAvailable(this)) {
			receiver.send(INTERNET_NOT_AVAILABLE, null);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Bundle bundle = new Bundle();
		bundle.putParcelable("location", location);

		receiver.send(LOCATION_CHANGED, bundle);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Bundle bundle = new Bundle();
		bundle.putString("provider", provider);

		receiver.send(LOCATION_PROVIDER_DISABLED, bundle);
	}

	@Override
	public void onProviderEnabled(String provider) {
		Bundle bundle = new Bundle();
		bundle.putString("provider", provider);

		receiver.send(LOCATION_PROVIDER_ENABLED, bundle);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Bundle bundle = new Bundle();
		bundle.putString("provider", provider);
		bundle.putInt("status", status);
		bundle.putBundle("extras", extras);

		receiver.send(LOCATION_STATUS_CHANGED, bundle);
	}
}