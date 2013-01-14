package net.dianw.logmyloc.constant;

public interface LocationConnectionStatus extends ConnectionStatus {
	public static final int LOCATION_PROVIDER_DISABLED = 4;
	public static final int LOCATION_PROVIDER_ENABLED = 5;
	public static final int LOCATION_CHANGED = 6;
	public static final int LOCATION_STATUS_CHANGED = 7;
}
