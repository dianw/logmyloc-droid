package org.meruvian.midas.view.lib;

import java.util.Date;

import net.dianw.logmyloc.R;
import net.dianw.logmyloc.activity.MapViewerActivity;

import org.meruvian.midas.activity.AboutActivity;
import org.meruvian.midas.view.adapter.NavigationMenuAdapter;
import org.meruvian.midas.view.lib.SideHorizontalScrollView.SizeCallback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

public class SideNavigationActivity extends ActionBarActivity implements
		OnItemClickListener {
	private SideHorizontalScrollView scrollView;
	private View menu;
	private boolean menuOut = false;
	private NavigationMenuAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (SideHorizontalScrollView) inflater.inflate(
				R.layout.horz_scroll_with_list_menu, null);
		setContentView(scrollView);
		menu = inflater.inflate(R.layout.horz_scroll_menu, null);

		ListView menuList = (ListView) menu.findViewById(R.id.bookmarks_list);
		mAdapter = new NavigationMenuAdapter(this);
		mAdapter.addSeparatorItem("Main Menu");
		mAdapter.addItem("Location List", R.drawable.ic_action_refresh);
		mAdapter.addItem("View Map", R.drawable.ic_action_search);
		mAdapter.addItem("Settings", android.R.drawable.ic_menu_manage);
		mAdapter.addItem("About", android.R.drawable.ic_menu_help);
		mAdapter.addItem("Exit", android.R.drawable.ic_menu_revert);

		menuList.setAdapter(mAdapter);
		menuList.setOnItemClickListener(this);
	}

	public void setContentViewWithNavigation(View app) {
		View[] children = new View[] { menu, app };
		int scrollToViewIdx = 1; // Scroll to app (view[1]) when layout
									// finished.
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(null));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			new ClickListenerForScrolling(scrollView, menu).onClick(null);
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Helper for examples with a HSV that should be scrolled by a menu View's
	 * width.
	 */
	class ClickListenerForScrolling implements OnClickListener {
		HorizontalScrollView scrollView;
		View menu;

		/**
		 * Menu must NOT be out/shown to start with.
		 */
		public ClickListenerForScrolling(HorizontalScrollView scrollView,
				View menu) {
			super();
			this.scrollView = scrollView;
			this.menu = menu;
		}

		@Override
		public void onClick(View v) {
			Log.d("SideNavigationActivity", "Slide " + new Date());
			int menuWidth = menu.getMeasuredWidth();
			menu.setVisibility(View.VISIBLE); // Ensure menu is visible

			if (!menuOut) {
				int left = 0; // Scroll to 0 to reveal menu
				scrollView.smoothScrollTo(left, 0);
			} else {
				int left = menuWidth; // Scroll to menuWidth so menu isn't on
										// screen.
				scrollView.smoothScrollTo(left, 0);
			}
			menuOut = !menuOut;
		}
	}

	/**
	 * Helper that remembers the width of the 'slide' button, so that the
	 * 'slide' button remains in view, even when the menu is showing.
	 */
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = 72;
			System.out.println("btnWidth=" + btnWidth);
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Context context = view.getContext();
		switch (position) {
		case 2:
			startActivity(new Intent(this, MapViewerActivity.class));
			break;
		case 6:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		default:
			break;
		}

	}
}
