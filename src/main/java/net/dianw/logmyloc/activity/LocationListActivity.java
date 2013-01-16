package net.dianw.logmyloc.activity;

import net.dianw.logmyloc.R;
import net.dianw.logmyloc.adapter.LocationAdapter;

import org.meruvian.midas.content.MidasContentProvider;
import org.meruvian.midas.content.database.MidasDatabase.Tables;
import org.meruvian.midas.view.lib.SideNavigationActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class LocationListActivity extends SideNavigationActivity implements
		LoaderCallbacks<Cursor> {
	private LayoutInflater inflater;
	private Uri dbUri = Uri.parse(MidasContentProvider.CONTENT_PATH
			+ Tables.PLACE);

	private LocationAdapter adapter;

	private ListView listLocation;
	private View mainLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = getLayoutInflater();

		mainLayout = inflater.inflate(R.layout.location_list, null);
		setContentViewWithNavigation(mainLayout);

		listLocation = findView(R.id.list_location);
		listLocation
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						onListItemClicked(parent, view, position, id);
					}
				});

		getSupportLoaderManager().initLoader(0, null, this);

		adapter = new LocationAdapter(this, null, true);
		listLocation.setAdapter(adapter);
	}

	protected void onListItemClicked(AdapterView<?> parent, View view,
			int position, long id) {
		Cursor cursor = (Cursor) adapter.getItem(position);

		Intent intent = new Intent(this, LocationFormActivity.class);
		intent.putExtra(BaseColumns._ID, cursor.getString(0));
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private View createListItem() {
		return inflater.inflate(R.layout.location_list_item, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.location_list_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_location:
			Intent intent = new Intent(this, LocationFormActivity.class);
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, dbUri, new String[] {
				"_id", "title", "description" }, null, null, "_id DESC");

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}
