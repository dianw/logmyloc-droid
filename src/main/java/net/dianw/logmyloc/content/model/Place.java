package net.dianw.logmyloc.content.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Place implements Serializable {
	private String id;
	@SerializedName("place_id")
	private String placeId;
	@SerializedName("osm_type")
	private String osmType;
	@SerializedName("osm_id")
	private String osmId;
	private double lat;
	private double lon;
	@SerializedName("display_name")
	private String displayName;
	private Address address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getOsmType() {
		return osmType;
	}

	public void setOsmType(String osmType) {
		this.osmType = osmType;
	}

	public String getOsmId() {
		return osmId;
	}

	public void setOsmId(String osmId) {
		this.osmId = osmId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
