package net.dianw.logmyloc.content.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Place implements Serializable {
	private String id;
	@SerializedName("place_id")
	private String placeId;
	private String title;
	private String description;
	@SerializedName("osm_type")
	private String osmType;
	@SerializedName("osm_id")
	private String osmId;
	private double lat = 0;
	private double lon = 0;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
