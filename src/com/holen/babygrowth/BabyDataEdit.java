package com.holen.babygrowth;

import java.util.Calendar;

import org.joda.time.Interval;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.holen.babygrowth.Constant.SqlConstant;
import com.holen.babygrowth.DB.DBHelper;

public class BabyDataEdit extends Activity {
	
	private Button saveBtn ;
	private EditText babyNameText;
	private RadioGroup gender;
	private EditText babyBirthText;
	private EditText babyWeightText;
	private EditText babyHeightText;
	private EditText babyPregnancyText;
	private EditText babyHeadText;
	private EditText babyBustText;
	
	private Calendar calendar;
	private int m_year , m_month , m_day;
	private long intervalTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baby_info);
		
		babyBirthText = (EditText)findViewById(R.id.babyBirth);
		saveBtn = (Button)findViewById(R.id.btnSave);
		gender = (RadioGroup)findViewById(R.id.genderGroup);
		babyNameText = (EditText)findViewById(R.id.babyName);
		babyWeightText = (EditText)findViewById(R.id.babyWeight);
		babyHeightText = (EditText)findViewById(R.id.babyHeight);
		babyPregnancyText = (EditText)findViewById(R.id.babyPregnancy);
		babyHeadText = (EditText)findViewById(R.id.babyHead);
		babyBustText = (EditText)findViewById(R.id.babyBust);
		
		//设置生日选择监听器
		babyBirthText.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((System.currentTimeMillis()-intervalTime)>1000){
					showDatePickerDialog();
					intervalTime = System.currentTimeMillis();
				}
			}
		});
		//保存按钮触发事件
		saveBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!validate()){
					return ;
				}
				// do some thing
				DBHelper helper = new DBHelper(getApplicationContext());
				String[] columns = {"baby_name"};
				String[] selectionArgs = {babyNameText.getText().toString()};
				Cursor c = helper.query(false, SqlConstant.babyTableName, columns , "baby_name=?", 
						selectionArgs, null, null, null, null);
				//Log.v("database", c.getCount());
				if (c.getCount() > 0){
					showAlert(getString(R.string.register_msg));
					babyNameText.setText("");
					return ;
				};
				ContentValues values= new ContentValues();
				values.put("baby_name", babyNameText.getText().toString());
				values.put("gender", gender.getCheckedRadioButtonId()==R.id.boy?"男":"女");
				values.put("birthday", m_year + "-" + (m_month+1) + "-" + m_day);
				values.put("weight", babyWeightText.getText().toString());
				values.put("height", babyHeightText.getText().toString());
				values.put("head_size", babyHeadText.getText().toString());
				values.put("bust", babyBustText.getText().toString());
				values.put("pregnancy", babyPregnancyText.getText().toString());
				helper.insert(SqlConstant.babyTableName, values);
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
		DatePickerDialog datePicker = new DatePickerDialog(BabyDataEdit.this, dateSetListener, m_year, m_month, m_day);
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

}
