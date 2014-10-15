package com.holen.babygrowth.DB;

import com.holen.babygrowth.Constant.SqlConstant;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "baby.db";
	private static final int DB_VERSION = 1;
	private SQLiteDatabase db;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;
		Log.v("database", "准备建表");
		db.execSQL(SqlConstant.babyTableCreating);
		db.execSQL(SqlConstant.dailyTableCreating);
		db.execSQL(SqlConstant.relationTableCreating);
		Log.v("database", "建表完成");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.v("database", "准备更新");
		db.execSQL(SqlConstant.relationTableDrop);
		db.execSQL(SqlConstant.babyTableDrop);
		db.execSQL(SqlConstant.dailyTableDrop);
		onCreate(db);
	}
	
	public void insert(String tableName , ContentValues values){
		if (db == null){
			db = getWritableDatabase();
		}
		Log.v("database", "开始添加数据");
		db.insert(tableName, null, values);
		close();
		Log.v("database", "添加数据完成");
	}
	
	public void update(String tableName , ContentValues values , String whereClause , String[] whereArgs){
		if (db == null){
			db = getWritableDatabase();
		}
		db.update(tableName, values, whereClause, whereArgs);
		close();
	}
	
	@SuppressLint("NewApi")
	public Cursor query(boolean distinct , String table , String[] columns , 
			String selection,String[] selectionArgs , String groupBy , String orderBy,
			String having , String limit){
		if (db == null){
			db = getWritableDatabase();
		}
		Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, null);
		return cursor;
	}
	
	public void delete(String table , String whereClause , String[] whereArgs){
		if(db == null){
			db = getWritableDatabase();
		}
		db.delete(table, whereClause, whereArgs);
		close();
	}
	
	public void close(){
		if (db != null){
			db.close();
		}
	}

}
