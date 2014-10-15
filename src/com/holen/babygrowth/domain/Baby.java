package com.holen.babygrowth.domain;

import org.joda.time.DateTime;

public class Baby {
	
	private String name;	
	private String gender;
	private DateTime birthday;
	private String weight;
	private String height;
	private String headSize;
	private String bust;
	private String pregnancy;
	
	public Baby(){
		
	}

	public Baby(String name, String gender, DateTime birthday, String weight,
			String height, String headSize, String bust, String pregnancy) {
		super();
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.weight = weight;
		this.height = height;
		this.headSize = headSize;
		this.bust = bust;
		this.pregnancy = pregnancy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public DateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeadSize() {
		return headSize;
	}

	public void setHeadSize(String headSize) {
		this.headSize = headSize;
	}

	public String getBust() {
		return bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	public String getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}

}
