package net.dianw.logmyloc.activity;

import net.dianw.logmyloc.R;

import org.meruvian.midas.view.lib.SideNavigationActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class LocationListActivity extends SideNavigationActivity {
	private ListView listLocation;
	private View mainLayout;
	private View listLocationItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mainLayout = getLayoutInflater().inflate(R.layout.location_list, null);
		setContentViewWithNavigation(mainLayout);

		listLocation = (ListView) mainLayout.findViewById(R.id.list_location);
		listLocationItem = getLayoutInflater().inflate(
				R.layout.location_list_item, null);
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
}
