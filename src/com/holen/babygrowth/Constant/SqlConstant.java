package com.holen.babygrowth.Constant;

public class SqlConstant {
	
	public static final String babyTableName = "baby";
	public static final String dailyTableName = "daily";
	public static final String relationTableName = "baby_relation";
	
	public static final String babyTableCreating = "create table "+ babyTableName + "(" + 
			"baby_id integer primary key autoincrement , " 
			 + "baby_name text not null , "
			 + "gender text not null , "
			 + "birthday text not null , " 
			 + "weight text default 0, " 
			 + "height text default 0 , " 
			 + "head_size text default 0 , " 
			 + "bust text default 0 , "
			 + "pregnancy text not null)";
	
	public static final String dailyTableCreating = "create table "+ dailyTableName +"(" 
			+ "info_id integer primary key autoincrement , " 
			+ "date text not null , "
			+ "weight text  , "
			+ "height text , "
			+ "head_size text , "
			+ "bust text)";
	
	public static final String relationTableCreating = "create table " +relationTableName + "("
			+ "baby_id integer primary key , "
			+ "info_id integer , "
			+ "foreign key(baby_id) references "+ babyTableName + "(baby_id) on delete cascade on update cascade , "
			+ "foreign key(info_id) references "+ dailyTableName + "(info_id) on delete cascade on update cascade)";
	
	public static final String babyTableDrop = "drop table if exist baby";
	
	public static final String dailyTableDrop = "drop table if exist dialy";
	
	public static final String relationTableDrop = "drop table if exist baby_relation";

}
