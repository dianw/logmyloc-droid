package net.dianw.logmyloc.activity;

import java.util.ArrayList;

import net.dianw.logmyloc.map.PointItemizedOverlay;
import net.dianw.logmyloc.service.MapLoaderService;

import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MapViewerActivity extends DefaultMapActivity implements
		OnItemGestureListener<OverlayItem> {

	private MapView mapView;
	private RelativeLayout relativeLayout;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;

	private PointItemizedOverlay<OverlayItem> itemizedOverlay;
	private TilesOverlay tilesOverlay;

	private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
	private MapTileProviderBasic tileProvider;

	@Override
	protected void onResume() {
		super.onResume();

		// Intent intent = new Intent(this, MapLoaderService.class);
		// intent.putExtra("receiver", receiver);
		// startService(intent);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Getting your location....");

		Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setPositiveButton("OK", null);
		alertDialog = alertDialogBuilder.create();

		mapView = new MapView(this, 256);
		mapView.setBuiltInZoomControls(true);
		mapView.setMultiTouchControls(true);
		mapView.getController().setZoom(5);

		tileProvider = new MapTileProviderBasic(this);
		tileProvider.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
		tileProvider.setUseDataConnection(true);

		tilesOverlay = new TilesOverlay(tileProvider, this);
		mapView.getOverlays().add(tilesOverlay);

		// relativeLayout = new RelativeLayout(this);
		// relativeLayout.addView(mapView, new RelativeLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// setContentView(relativeLayout);

		setContentViewWithNavigation(mapView);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

		switch (resultCode) {
		case CONNECTION_STARTED:
			progressDialog.show();

			break;
		case INTERNET_NOT_AVAILABLE:
			alertDialog.setMessage("No internet connection available!");
			alertDialog.show();

			break;
		case CONNECTION_FINISHED:
			break;
		}

		super.onReceiveResult(resultCode, resultData);
	}

	@Override
	public void onLocationChanged(Location location) {
		// items = new ArrayList<OverlayItem>();
		//
		// GeoPoint center = new GeoPoint(location);
		//
		// items.add(new OverlayItem("You're here", "Your current location",
		// center));
		// itemizedOverlay = new PointItemizedOverlay<OverlayItem>(items, this
		// .getResources().getDrawable(android.R.drawable.star_big_on),
		// this, this);
		// mapView.getOverlays().add(itemizedOverlay);
		// mapView.getController().setZoom(11);
		// mapView.getController().setCenter(center);
	}

	@Override
	public boolean onItemSingleTapUp(int index, OverlayItem item) {
		return false;
	}

	@Override
	public boolean onItemLongPress(int index, OverlayItem item) {
		return false;
	}
}