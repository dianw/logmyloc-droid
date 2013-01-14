package org.meruvian.midas.view.lib;

import android.view.View;

/**
 * Utility methods for Views.
 */
public class ViewUtils {
	private ViewUtils() {
	}

	public static void setViewWidths(View view, View[] views) {
		int w = view.getWidth();
		int h = view.getHeight();
		for (int i = 0; i < views.length; i++) {
			View v = views[i];
			v.layout((i + 1) * w, 0, (i + 2) * w, h);
			printView("view[" + i + "]", v);
		}
	}

	public static void printView(String msg, View v) {
		System.out.println(msg + "=" + v);
		if (null == v) {
			return;
		}
		System.out.print("[" + v.getLeft());
		System.out.print(", " + v.getTop());
		System.out.print(", w=" + v.getWidth());
		System.out.println(", h=" + v.getHeight() + "]");
		System.out.println("mw=" + v.getMeasuredWidth() + ", mh="
				+ v.getMeasuredHeight());
		System.out.println("scroll [" + v.getScrollX() + "," + v.getScrollY()
				+ "]");
	}
}
