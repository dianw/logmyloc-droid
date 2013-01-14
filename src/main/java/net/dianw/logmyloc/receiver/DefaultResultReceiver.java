package net.dianw.logmyloc.receiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DefaultResultReceiver extends ResultReceiver {
	private Receiver receiver;

	public DefaultResultReceiver(Handler handler, Receiver receiver) {
		super(handler);
		this.receiver = receiver;
	}

	public static interface Receiver {
		void onReceiveResult(int resultCode, Bundle resultData);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		receiver.onReceiveResult(resultCode, resultData);
	}
}
