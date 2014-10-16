package com.holen.babygrowth;

import java.util.Calendar;

import org.joda.time.Interval;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.holen.babygrowth.Constant.SqlConstant;
import com.holen.babygrowth.DB.DBHelper;

public class BabyInfoReferring extends Activity {
	
	private EditText babyBirthText;
	private Button saveBtn;
	private RadioGroup gender;
	private EditText babyNameText;
	private EditText babyWeightText;
	private EditText babyHeightText;
	private EditText babyPregnancyText;
	private EditText babyHeadText;
	private EditText babyBustText;
	private RadioButton boyButton;
	private RadioButton girlButton;
	
	private Calendar calendar;
	private int m_year , m_month , m_day;
	private long intervalTime = 0;
	
	private DBHelper helper = new DBHelper(this);
	private String name = null;
	private String baby_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baby_info);
		
		Intent intent = this.getIntent();
		name = intent.getStringExtra("name");
		
		babyBirthText = (EditText)findViewById(R.id.babyBirth);
		saveBtn = (Button)findViewById(R.id.btnSave);
		gender = (RadioGroup)findViewById(R.id.genderGroup);
		babyNameText = (EditText)findViewById(R.id.babyName);
		babyWeightText = (EditText)findViewById(R.id.babyWeight);
		babyHeightText = (EditText)findViewById(R.id.babyHeight);
		babyPregnancyText = (EditText)findViewById(R.id.babyPregnancy);
		babyHeadText = (EditText)findViewById(R.id.babyHead);
		babyBustText = (EditText)findViewById(R.id.babyBust);
		boyButton = (RadioButton)findViewById(R.id.boy);
		girlButton = (RadioButton)findViewById(R.id.girl);
		showBabyInfo(name);
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
		int id = item.getItemId();
		if (id == R.id.info_edit){
			showBabyEdit();
		}else if(id == R.id.info_delete){
			deleteDialog(getString(R.string.delete_baby_msg), "baby_name=?", new String[]{name});
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showBabyInfo(String name){
		Cursor c = helper.query(false, SqlConstant.babyTableName, null, "baby_name=?", new String[]{name}
			, null, null, null, null);
		if (c.moveToFirst()){
			baby_id = c.getString(0);
			babyNameText.setText(c.getString(1));
			babyNameText.setFocusableInTouchMode(false);
			/*gender.check(c.getString(2).equals("男")?R.id.boy:R.id.girl);
			boyButton.setEnabled(false);
			girlButton.setEnabled(false);*/
			if (c.getString(2).equals("男")){
				gender.check(R.id.boy);
				girlButton.setEnabled(false);
			}else{
				gender.check(R.id.girl);
				boyButton.setEnabled(false);
			}
			//babyBirthText.setText(c.getString(3).replaceFirst("-", "年").replaceFirst("-", "月") + "日");
			String[] date = c.getString(3).split("-");
			m_year = Integer.parseInt(date[0]);
			m_month = Integer.parseInt(date[1])-1;
			m_day = Integer.parseInt(date[2]);
			babyBirthText.setText(m_year + "年" + (m_month+1) + "月" + m_day + "日");
			babyBirthText.setOnClickListener(null);
			babyWeightText.setText(c.getString(4));
			babyWeightText.setFocusableInTouchMode(false);
			babyHeightText.setText(c.getString(5));
			babyHeightText.setFocusableInTouchMode(false);
			babyHeadText.setText(c.getString(6));
			babyHeadText.setFocusableInTouchMode(false);
			babyBustText.setText(c.getString(7));
			babyBustText.setFocusableInTouchMode(false);
			babyPregnancyText.setText(c.getString(8));
			babyPregnancyText.setFocusableInTouchMode(false);
			saveBtn.setVisibility(View.INVISIBLE);
		}
	}
	
	private void showBabyEdit(){
		babyNameText.setFocusableInTouchMode(true);
		boyButton.setEnabled(true);
		girlButton.setEnabled(true);
		babyBirthText.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((System.currentTimeMillis()-intervalTime)>1000){
					showDatePickerDialog();
					intervalTime = System.currentTimeMillis();
				}
			}
		});
		babyWeightText.setFocusableInTouchMode(true);
		babyHeightText.setFocusableInTouchMode(true);
		babyHeadText.setFocusableInTouchMode(true);
		babyBustText.setFocusableInTouchMode(true);
		babyPregnancyText.setFocusableInTouchMode(true);
		saveBtn.setVisibility(View.VISIBLE);
		
		saveBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("baby_name", babyNameText.getText().toString());
				values.put("gender", gender.getCheckedRadioButtonId()==R.id.boy?"男":"女");
				values.put("birthday", m_year + "-" + (m_month+1) + "-" + m_day);
				values.put("weight", babyWeightText.getText().toString());
				values.put("height", babyHeightText.getText().toString());
				values.put("head_size", babyHeadText.getText().toString());
				values.put("bust", babyBustText.getText().toString());
				values.put("pregnancy", babyPregnancyText.getText().toString());
				helper.update(SqlConstant.babyTableName, values, "baby_id=?", new String[]{baby_id});
				Toast.makeText(BabyInfoReferring.this, R.string.toast_update , Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
	
	/**
	 * 判断是否存在空值，如果存在空值，不写入数据库
	 */
	private boolean validate(){
		String weight = babyWeightText.getText().toString();
		String height = babyHeightText.getText().toString();
		String head = babyHeadText.getText().toString();
		String bust = babyBustText.getText().toString();
		String pregnancy = babyPregnancyText.getText().toString();
		//当randioButton不选择时，是-1
		if(babyNameText.getText().toString().trim().equals("") || gender.getCheckedRadioButtonId()==-1
				|| babyBirthText.getText().toString().equals("") || weight.equals("")
				|| height.equals("") || pregnancy.equals("")
				|| bust.equals("") || head.equals("")){
			showAlert(getString(R.string.info_msg));
			return false;
		}
		try {
			if(Double.parseDouble(weight)<0 || Double.parseDouble(height)<0 || Double.parseDouble(head)<0
					|| Double.parseDouble(bust)<0 || Double.parseDouble(pregnancy)<0){
				showAlert(getString(R.string.warn_msg));
				return false;
			}
		} catch (NumberFormatException ex) {
			showAlert(getString(R.string.warn_msg));
			return false;
		}
		
		return true;
	}
	
	/**
	 * 显示时间选择器对话框
	 */
	private void showDatePickerDialog(){
		calendar = Calendar.getInstance();
		
		m_year = calendar.get(Calendar.YEAR);
		m_month = calendar.get(Calendar.MONTH);
		m_day = calendar.get(Calendar.DAY_OF_MONTH);
		Log.v("holen", m_year + "年" + m_month + "月" + m_day);
		//showAlert(m_year + "");
		DatePickerDialog datePicker = new DatePickerDialog(BabyInfoReferring.this, dateSetListener, m_year, m_month, m_day);
		datePicker.show();
	}
	
	/**
	 * 事件选择器对话框监听器
	 */
	private OnDateSetListener dateSetListener = new OnDateSetListener() {
		
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			m_year = year;
			m_month = monthOfYear;
			m_day = dayOfMonth;
			babyBirthText.setText(m_year + "年" + (m_month+1) + "月" + m_day + "日");
		}
	};
	
	/**
	 *  显示警告框
	 */
	private void showAlert(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
			.setCancelable(false)
			.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
private void deleteDialog(String message , String whereClause , String[] whereArgs){
		
		final String _whereClause = whereClause;
		final String[] _whereArgs = whereArgs;
		AlertDialog.Builder builder = new Builder(BabyInfoReferring.this);
		
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
				Toast.makeText(BabyInfoReferring.this, R.string.toast_delete , Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		builder.create().show();
	}
	
}
