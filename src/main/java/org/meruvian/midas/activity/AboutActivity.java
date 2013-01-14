package org.meruvian.midas.activity;

import net.dianw.logmyloc.R;

import org.meruvian.midas.view.lib.ActionBarActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		((WebView) findViewById(R.id.aboutWebview))
				.loadUrl("file:///android_asset/about.html");

	}
}
