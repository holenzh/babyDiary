package com.holen.babygrowth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.holen.babygrowth.DB.DBHelper;

public class BabyInfoLooking_test extends Activity {
	
	private EditText babyBirthText = (EditText)findViewById(R.id.babyBirth);
	private Button saveBtn = (Button)findViewById(R.id.btnSave);
	private RadioGroup gender = (RadioGroup)findViewById(R.id.genderGroup);
	private EditText babyNameText = (EditText)findViewById(R.id.babyName);
	private EditText babyWeightText = (EditText)findViewById(R.id.babyWeight);
	private EditText babyHeightText = (EditText)findViewById(R.id.babyHeight);
	private EditText babyPregnancyText = (EditText)findViewById(R.id.babyPregnancy);
	private EditText babyHeadText = (EditText)findViewById(R.id.babyHead);
	private EditText babyBustText = (EditText)findViewById(R.id.babyBust);
	
	DBHelper helper = new DBHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baby_info_beta);
		/*Intent intent = this.getIntent();
		String name = intent.getExtras().getString("name");
		Log.v("holen", "name是" + name);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.baby_info_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 捕捉返回事件按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			finish();
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	private void showBabyInfo(String name){
		
	}

}
