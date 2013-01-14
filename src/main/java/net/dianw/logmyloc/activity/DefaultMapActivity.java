package net.dianw.logmyloc.activity;

import net.dianw.logmyloc.constant.LocationConnectionStatus;
import net.dianw.logmyloc.receiver.DefaultResultReceiver;
import net.dianw.logmyloc.receiver.DefaultResultReceiver.Receiver;
import net.dianw.logmyloc.service.MapLoaderService;

import org.meruvian.midas.view.lib.SideNavigationActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DefaultMapActivity extends SideNavigationActivity implements
		LocationConnectionStatus, Receiver, LocationListener {
	protected AlertDialog alertDialog;
	protected ResultReceiver receiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		receiver = new DefaultResultReceiver(new Handler(), this);

		Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("No Network");
		alertDialogBuilder.setMessage("No network detected");
		alertDialogBuilder.setNegativeButton("Cancel", null);
		alertDialogBuilder.setPositiveButton("Network Setting",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivityForResult(
								new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS),
								0);
					}
				});
		alertDialog = alertDialogBuilder.create();
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
		case LOCATION_PROVIDER_DISABLED: {
			onProviderDisabled(resultData.getString("provider"));

			break;
		}
		case LOCATION_PROVIDER_ENABLED: {
			onProviderEnabled(resultData.getString("provider"));

			break;
		}
		case LOCATION_CHANGED: {
			Location location = resultData.getParcelable("location");
			onLocationChanged(location);

			break;
		}
		case LOCATION_STATUS_CHANGED: {
			onStatusChanged(resultData.getString("provider"),
					resultData.getInt("status"), resultData.getBundle("extras"));

			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {
		alertDialog.show();
	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
}
