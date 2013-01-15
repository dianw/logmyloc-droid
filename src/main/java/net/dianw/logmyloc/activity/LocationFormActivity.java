package net.dianw.logmyloc.activity;

import net.dianw.logmyloc.R;
import net.dianw.logmyloc.content.model.Address;
import net.dianw.logmyloc.content.model.Place;
import net.dianw.logmyloc.service.NominatimRestService;

import org.meruvian.midas.content.MidasContentProvider;
import org.meruvian.midas.content.database.MidasDatabase.Tables;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuItem;
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

	private String id = null;
	private ContentResolver db;
	private Uri dbUri = Uri.parse(MidasContentProvider.CONTENT_PATH
			+ Tables.PLACE);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = getContentResolver();

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

		if (savedInstanceState != null) {
			id = savedInstanceState.getString(BaseColumns._ID);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.location_form_menu, menu);

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (id == null) {
			Intent intent = new Intent(this, NominatimRestService.class);
			intent.putExtra("receiver", receiver);
			startService(intent);
		} else {
			CursorLoader loader = new CursorLoader(this);
			loader.setSelection("_id = ?");
			loader.setSelectionArgs(new String[] { id });
			loader.setSortOrder("id ASC");
			loader.setUri(dbUri);

			Cursor cursor = loader.loadInBackground();
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				Place place = new Place();
				place.setDisplayName(cursor.getString(cursor
						.getColumnIndex("display_name")));
				place.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				place.setDescription(cursor.getString(cursor
						.getColumnIndex("description")));
				place.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
				place.setLon(cursor.getDouble(cursor.getColumnIndex("lng")));

				Address address = place.getAddress();
				address.setRoad(cursor.getString(cursor
						.getColumnIndex("address_road")));
				address.setCity(cursor.getString(cursor
						.getColumnIndex("address_city")));
				address.setState(cursor.getString(cursor
						.getColumnIndex("address_state")));
				address.setCountry(cursor.getString(cursor
						.getColumnIndex("address_country")));
				address.setPostcode(cursor.getString(cursor
						.getColumnIndex("address_postcode")));

				initFields(place);
			}

			loader.deliverResult(cursor);
		}
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			savePlace();
			break;
		default:
			break;
		}

		return true;
	}

	private void savePlace() {
		ContentValues values = new ContentValues();
		values.put("title", txtTitle.getText().toString());
		values.put("description", txtDescription.getText().toString());
		values.put("lat", Double.parseDouble(txtLatitude.getText().toString()));
		values.put("lng", Double.parseDouble(txtLongitude.getText().toString()));
		values.put("display_name", txtDetails.getText().toString());
		values.put("address_road", txtRoad.getText().toString());
		values.put("address_city", txtCity.getText().toString());
		values.put("address_state", txtState.getText().toString());
		values.put("address_postcode", txtPostcode.getText().toString());
		values.put("address_country", txtCountry.getText().toString());

		if (id == null) {
			db.insert(dbUri, values);
		} else {
			db.update(dbUri, values, "_id = ?", new String[] { id });
		}

		finish();
	}

	@Override
	public void onLocationChanged(Location location) {
		if (progressDialog.isShowing())
			progressDialog.dismiss();

		Place place = (Place) location.getExtras().getSerializable("place");
		initFields(place);
	}

	private void initFields(Place place) {
		if (place == null)
			return;
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
