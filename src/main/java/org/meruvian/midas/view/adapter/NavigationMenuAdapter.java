package org.meruvian.midas.view.adapter;

import java.util.ArrayList;
import java.util.TreeSet;

import net.dianw.logmyloc.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationMenuAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

	private ArrayList<MenuItem> mData = new ArrayList<MenuItem>();
	private LayoutInflater mInflater;

	private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

	public NavigationMenuAdapter(Context ctx) {
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addItem(final String title, final int resourceDrawable) {
		mData.add(new MenuItem(title, resourceDrawable));
		notifyDataSetChanged();
	}

	public void addSeparatorItem(final String title) {
		mData.add(new MenuItem(title, 0));
		mSeparatorsSet.add(mData.size() - 1); // save separator position
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	public int getCount() {
		return mData.size();
	}

	public MenuItem getItem(int position) {
		return mData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case TYPE_ITEM:
				convertView = mInflater.inflate(R.layout.bookmark_item, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.bookmark_item_label);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.bookmark_item_icon);
				holder.imageView
						.setImageResource(mData.get(position).resourceDrawable);
				holder.count = (TextView) convertView
						.findViewById(R.id.bookmark_item_count);
				break;
			case TYPE_SEPARATOR:
				convertView = mInflater
						.inflate(R.layout.bookmark_divider, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.bookmark_item_label);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mData.get(position).title);
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
		public TextView count;
	}

	public class MenuItem {
		public String title;
		public int resourceDrawable;

		public MenuItem(String title, int resourceDrawable) {
			this.title = title;
			this.resourceDrawable = resourceDrawable;
		}
	}
}