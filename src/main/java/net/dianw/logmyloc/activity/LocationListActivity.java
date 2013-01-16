package net.dianw.logmyloc.activity;

import net.dianw.logmyloc.R;
import net.dianw.logmyloc.adapter.LocationAdapter;

import org.meruvian.midas.content.MidasContentProvider;
import org.meruvian.midas.content.database.MidasDatabase.Tables;
import org.meruvian.midas.view.lib.SideNavigationActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class LocationListActivity extends SideNavigationActivity implements
		LoaderCallbacks<Cursor> {
	private LayoutInflater inflater;
	private Uri dbUri = Uri.parse(MidasContentProvider.CONTENT_PATH
			+ Tables.PLACE);

	private String id = null;
	private LocationAdapter adapter;

	private ListView listLocation;
	private View mainLayout;
	private AlertDialog menuDialog;
	private AlertDialog deleteConfirmDialog;
	private AlertDialog noListDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = getLayoutInflater();

		mainLayout = inflater.inflate(R.layout.location_list, null);
		setContentViewWithNavigation(mainLayout);

		listLocation = findView(R.id.list_location);
		listLocation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onListItemClicked(parent, view, position, id);
			}
		});

		listLocation.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				return onListItemLongClick(parent, view, position, id);
			}
		});

		String[] menus = { "View details", "Delete" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose action");
		builder.setItems(menus, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					openDetails();
					break;
				case 1:
					deleteConfirmDialog.show();
					break;
				default:
					break;
				}
			}
		});
		menuDialog = builder.create();

		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.menu_delete);
		builder.setMessage("The selected location will be deleted.");
		builder.setPositiveButton(R.string.menu_delete, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getContentResolver().delete(dbUri, "_id = ?",
						new String[] { id });
			}
		});
		builder.setNegativeButton("Cancel", null);
		deleteConfirmDialog = builder.create();

		builder = new AlertDialog.Builder(this);
		builder.setTitle("Locations");
		builder.setMessage("You currently have no place listed on your account.");
		builder.setPositiveButton(R.string.menu_add_location,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						openLocationForm();
					}
				});
		noListDialog = builder.create();

		getSupportLoaderManager().initLoader(0, null, this);

		adapter = new LocationAdapter(this, null, true);
		listLocation.setAdapter(adapter);
	}

	protected boolean onListItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Cursor cursor = (Cursor) adapter.getItem(position);
		this.id = cursor.getString(0);
		menuDialog.show();

		return true;
	}

	protected void onListItemClicked(AdapterView<?> parent, View view,
			int position, long id) {
		Cursor cursor = (Cursor) adapter.getItem(position);
		this.id = cursor.getString(0);
		openDetails();
	}

	private void openDetails() {
		if (this.id != null) {
			Intent intent = new Intent(this, LocationFormActivity.class);
			intent.putExtra(BaseColumns._ID, this.id);

			this.id = null;

			startActivity(intent);
		}
	}

	protected void openLocationForm() {
		Intent intent = new Intent(LocationListActivity.this,
				LocationFormActivity.class);
		startActivity(intent);
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
			openLocationForm();
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
		if (cursor.getCount() < 1) {
			noListDialog.show();
		}

		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}
