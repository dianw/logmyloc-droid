package net.dianw.logmyloc.adapter;

import net.dianw.logmyloc.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationAdapter extends CursorAdapter {

	public LocationAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.location_list_item, parent, false);
		bindView(v, context, cursor);

		return v;
	}

	@Override
	public void bindView(View v, Context context, Cursor cursor) {
		TextView txtTitle = (TextView) v.findViewById(R.id.text_location_name);
		TextView txtDesc = (TextView) v
				.findViewById(R.id.text_location_description);

		txtTitle.setText(cursor.getString(1));
		txtDesc.setText(cursor.getString(2));
	}
}
