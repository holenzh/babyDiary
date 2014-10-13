package com.holen.babygrowth;

import org.joda.time.DateTime;

public class Baby {
	
	private String name;	
	private String sex;
	private DateTime birthday;
	private int weight;
	private int height;
	private int pregnantTime;
	
	public Baby(){
		
	}

	public Baby(String name, String sex, DateTime birthday, int weight,
			int height, int pregnantTime) {
		super();
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.weight = weight;
		this.height = height;
		this.pregnantTime = pregnantTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public DateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPregnantTime() {
		return pregnantTime;
	}

	public void setPregnantTime(int pregnantTime) {
		this.pregnantTime = pregnantTime;
	}
	
	

}
