package com.ovr.model;

import java.io.InputStream;
import java.util.List;

public class Vehicle {

	private int id;
	private String manufacturer;
	private String model;
	private String description;
	private InputStream image;
	private double dailyFare;
	private String fromDate;
	private String toDate;
	private String featureStatus;
	
	public Vehicle(){
		
	}
	
	public Vehicle(int id) {
		this.id = id;
	}
	
	public String getFeatureStatus() {
		return featureStatus;
	}

	public void setFeatureStatus(String featureStatus) {
		this.featureStatus = featureStatus;
	}

	private int ownerId;
	private int vehicleCurrentLocationId;
	private List<Integer> vehicleEndLocationIds;

	private Owner owner;
	private District district;
	private List<District> districtList;

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public double getDailyFare() {
		return dailyFare;
	}

	public void setDailyFare(double dailyFare) {
		this.dailyFare = dailyFare;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getVehicleCurrentLocationId() {
		return vehicleCurrentLocationId;
	}

	public void setVehicleCurrentLocationId(int vehicleCurrentLocationId) {
		this.vehicleCurrentLocationId = vehicleCurrentLocationId;
	}

	public List<Integer> getVehicleEndLocationIds() {
		return vehicleEndLocationIds;
	}

	public void setVehicleEndLocationIds(List<Integer> vehicleEndLocationIds) {
		this.vehicleEndLocationIds = vehicleEndLocationIds;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
