package org.meruvian.midas.activity;

import net.dianw.logmyloc.R;

import org.meruvian.midas.view.lib.SideNavigationActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class AboutActivity extends SideNavigationActivity {
	private View mainLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mainLayout = getLayoutInflater().inflate(R.layout.about, null);
		setContentViewWithNavigation(mainLayout);

		((WebView) mainLayout.findViewById(R.id.aboutWebview))
				.loadUrl("file:///android_asset/about.html");
	}
}
