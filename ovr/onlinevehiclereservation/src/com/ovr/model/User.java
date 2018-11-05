package com.ovr.model;

import java.io.InputStream;

public class User {

	private int id;
	private String email;
	private String password;
	private String fullname;
	private String contactNo;
	private String address;
	private InputStream profileImage;
	private InputStream citizenship;
	private InputStream drivingLicense;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public InputStream getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(InputStream profileImage) {
		this.profileImage = profileImage;
	}

	public InputStream getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(InputStream citizenship) {
		this.citizenship = citizenship;
	}

	public InputStream getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(InputStream drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
