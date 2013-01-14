package net.dianw.logmyloc.activity;

import net.dianw.logmyloc.R;
import net.dianw.logmyloc.content.model.Place;
import net.dianw.logmyloc.service.NominatimRestService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LocationFormActivity extends DefaultMapActivity {
	private View mainLayout;
	private ProgressDialog progressDialog;

	private EditText txtTitle;
	private EditText txtDescription;
	private EditText txtLatitude;
	private EditText txtLongitude;
	private EditText txtRoad;
	private EditText txtCity;
	private EditText txtState;
	private EditText txtPostcode;
	private EditText txtCountry;
	private EditText txtDetails;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mainLayout = getLayoutInflater().inflate(R.layout.location_form, null);
		setContentViewWithNavigation(mainLayout);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Getting your current location...");

		alertDialog.setMessage("We couldn't get your current location");

		txtTitle = findView(R.id.text_title);
		txtDescription = findView(R.id.text_description);
		txtLatitude = findView(R.id.text_latitude);
		txtLongitude = findView(R.id.text_longitude);
		txtRoad = findView(R.id.text_road);
		txtCity = findView(R.id.text_city);
		txtState = findView(R.id.text_state);
		txtPostcode = findView(R.id.text_postcode);
		txtCountry = findView(R.id.text_country);
		txtDetails = findView(R.id.text_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.location_form_menu, menu);

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		Intent intent = new Intent(this, NominatimRestService.class);
		intent.putExtra("receiver", receiver);
		startService(intent);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (progressDialog.isShowing())
			progressDialog.dismiss();

		switch (resultCode) {
		case CONNECTION_STARTED:
			progressDialog.show();

			break;
		default:
			break;
		}

		super.onReceiveResult(resultCode, resultData);
	}

	@Override
	public void onLocationChanged(Location location) {
		if (progressDialog.isShowing())
			progressDialog.dismiss();

		Place place = (Place) location.getExtras().getSerializable("place");
		txtLatitude.setText(Double.toString(place.getLat()));
		txtLongitude.setText(Double.toString(place.getLon()));
		txtRoad.setText(place.getAddress().getRoad());
		txtCity.setText(place.getAddress().getCity());
		txtState.setText(place.getAddress().getState());
		txtPostcode.setText(place.getAddress().getPostcode());
		txtCountry.setText(place.getAddress().getCountry());
		txtDetails.setText(place.getDisplayName());
	}
}
