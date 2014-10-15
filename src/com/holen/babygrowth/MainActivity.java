package com.holen.babygrowth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.holen.babygrowth.Constant.SqlConstant;
import com.holen.babygrowth.DB.DBHelper;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		showBabyList();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("holen", "我恢复了。。。");
	}

	/**
	 * 添加菜单选项
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 菜单选项监听器
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.main_addBaby) {
			Intent intent = new Intent(MainActivity.this , BabyDataEdit.class);
			startActivity(intent);
			return true;
		}else if(id == R.id.main_about){
			return true;
		}else if(id == R.id.main_exit){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/**
	 * 捕捉返回事件按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			exitApp();
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	/**
	 * 退出程序逻辑
	 */
	public void exitApp(){
		if ((System.currentTimeMillis() - exitTime) > 2000){
			Toast.makeText(MainActivity.this, R.string.toast_exit , Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		}else{
			finish();
		}
	}
	
	public void showBabyList(){
		DBHelper helper = new DBHelper(this);
		String[] columns = {"baby_name" , "gender" , "birthday"};
		Cursor c = helper.query(false, SqlConstant.babyTableName, columns, null, null, null, null, null, null);
		Log.v("holen", "haha" + c.getCount());
		if (c.getCount() == 0){
			setContentView(R.layout.fragment_main);
		}else{
			setContentView(R.layout.baby_list);
			List<Map<String, String>> listItem = new ArrayList<Map<String,String>>();
			Map<String, String> paramMap = new HashMap<String, String>();
			if (c.moveToFirst()){
				for (int i=0 ; i<c.getCount() ; i++){
					c.move(i);
					paramMap.put("name", c.getString(0));
					paramMap.put("gender", c.getString(1));
					
					DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
					DateTime startTime = DateTime.parse(c.getString(2), format);
					paramMap.put("age", calcDayToNow(startTime, new DateTime()));
					listItem.add(paramMap);
				}
			}
			
			SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItem, R.layout.simple_item, 
					new String[]{"name" , "gender" , "age"}, new int[]{R.id.showName , R.id.showGender , R.id.showAge});
			ListView listView = (ListView)findViewById(R.id.babyList);
			listView.setAdapter(simpleAdapter);
		}
	}
	
	public String calcDayToNow(DateTime startTime , DateTime endTime){
		LocalDate start = new LocalDate(startTime);
		LocalDate end = new LocalDate(endTime);
		Days days = Days.daysBetween(start, end);
		int intervalDays = days.getDays();
		if (intervalDays > 365){
			return intervalDays/365 + "周岁";
		}else if  (intervalDays > 200){
			return intervalDays/30 + "个月";
		}else{
			return intervalDays + "天";
		}
	}

}
