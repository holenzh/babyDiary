package com.holen.babygrowth.DB;

import com.holen.babygrowth.Constant.SqlConstant;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BabyDbHandler {
	
	private SQLiteDatabase db;
	private Context context;
	private DBHelper helper;

	public BabyDbHandler(Context context) {
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public static BabyDbHandler open(Context context){
		 BabyDbHandler handler = null;
		 try {
			handler = new BabyDbHandler(context);
		} catch (SQLException e) {
			// TODO: handle exception
			Log.v("holen", "打开数据库失败：" + e.getMessage());
		}
		 return handler;
	}
	
	public void close(){
		helper.close();
	}
	
	public long insert(String babyName,String gender,String birthDay,String weight,String height,String headSize
			,String pregnancy){
		ContentValues values = new ContentValues();
		values.put("baby_name", babyName);
		values.put("gender", gender);
		values.put("birthday", birthDay);
		values.put("weight", weight);
		values.put("height", height);
		values.put("head_size", headSize);
		values.put("pregnancy", pregnancy);
		return db.insert(SqlConstant.babyTableName, null, values);
	}
	

}
