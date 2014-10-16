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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	private long exitTime = 0;
	private List<Map<String, String>> listItem = new ArrayList<Map<String,String>>();
	private SimpleAdapter simpleAdapter = null;
	private ListView listView = null;
	private DBHelper helper = new DBHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		showBabyList();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showBabyList();
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
			/*View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);*/
			View rootView = null;
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
		listItem.clear();
		String[] columns = {"baby_name" , "gender" , "birthday"};
		Cursor c = helper.query(false, SqlConstant.babyTableName, columns, null, null, null, null, null, null);
		Log.v("holen", "count的值" + c.getCount());
		if (c.getCount() == 0){
			setContentView(R.layout.fragment_main);
		}else{
			setContentView(R.layout.baby_list);
			
			if(c.moveToFirst()){
				for (int i=0 ; i<c.getCount() ; i++){
					Map<String, String> paramMap = new HashMap<String, String>();
					c.moveToPosition(i);
					//Log.v("holen", "姓名1 --> " + c.getString(0));
					paramMap.put("name", c.getString(0));
					paramMap.put("gender", c.getString(1));
					DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
					DateTime startTime = DateTime.parse(c.getString(2), format);
					paramMap.put("age", calcDayToNow(startTime, new DateTime()));
					listItem.add(paramMap);
					//Log.v("holen", "清空map");
				}
			}
			
			simpleAdapter = new SimpleAdapter(this, listItem, R.layout.simple_item, 
					new String[]{"name" , "gender" , "age"}, new int[]{R.id.showName , R.id.showGender , R.id.showAge});
			listView = (ListView)findViewById(R.id.babyList);
			listView.setAdapter(simpleAdapter);
			this.registerForContextMenu(listView);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				/**
				 * 点击列表时触发方法
				 * @param: adapaterView:点击事件的adapterView
				 * @param: view:adapterview中被选中的view
				 * @param: position:当前点击的行在adapter的下标
				 * @param: id:当前按点击行的id
				 */
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.add(0, Menu.FIRST, 0, R.string.look_info);
		menu.add(0, Menu.FIRST+1, 0, R.string.add_baby);
		menu.add(0, Menu.FIRST+2, 0, R.string.delete_baby);
		menu.add(0, Menu.FIRST+3, 0, R.string.delete_all);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		switch (item.getItemId()){
		case 1:
			Log.v("holen", "查看资料");
			//Log.v("holen",	listItem.get((int)info.id).get("name"));
			break;
		case 2:
			Log.v("holen", "添加babay");
			Intent intent = new Intent(MainActivity.this , BabyDataEdit.class);
			startActivity(intent);
			break;
		case 3:
			Log.v("holen", "删除babay");
			String[] whereArgs = {listItem.get((int)info.id).get("name")};
			deleteDialog(getString(R.string.delete_baby_msg), "baby_name=?" , whereArgs);
			break;
		case 4:
			Log.v("holen", "删除全部");
			deleteDialog(getString(R.string.delete_all_msg), null , null);
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void deleteDialog(String message , String whereClause , String[] whereArgs){
		
		final String _whereClause = whereClause;
		final String[] _whereArgs = whereArgs;
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		
		builder.setMessage(message)
			.setTitle(getString(R.string.warn));
		
		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {			
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					return ;
				}
			});
		builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.v("holen", "_whereArgs " + _whereArgs[0]);
				helper.delete(SqlConstant.babyTableName, _whereClause, _whereArgs);
				Toast.makeText(MainActivity.this, R.string.toast_delete , Toast.LENGTH_SHORT).show();
				showBabyList();
			}
		});
		builder.create().show();
	}
	
	private String calcDayToNow(DateTime startTime , DateTime endTime){
		LocalDate start = new LocalDate(startTime);
		LocalDate end = new LocalDate(endTime);
		Days days = Days.daysBetween(start, end);
		int intervalDays = days.getDays();
		if (intervalDays >= 365){
			return intervalDays/365 + "周岁";
		}else if  (intervalDays > 200){
			return intervalDays/30 + "个月";
		}else{
			return intervalDays + "天";
		}
	}

}
