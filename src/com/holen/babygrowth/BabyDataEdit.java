package com.holen.babygrowth;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BabyDataEdit extends Activity {
	
	private Button saveBtn ;
	private EditText babyNameText;
	private RadioGroup gender;
	private EditText babyBirthText;
	private EditText babyWeightText;
	private EditText babyHeightText;
	private EditText babyPregnancyText;
	
	private Calendar calendar;
	private int m_year , m_month , m_day;

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
		
		//设置生日选择监听器
		babyBirthText.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				showDatePickerDialog();
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
			}
		});
		
	}
	/**
	 * 判断是否存在空值，如果存在空值，不写入数据库
	 */
	private boolean validate(){
		//当randioButton不选择时，是-1
		if(babyNameText.getText().toString().equals("") || gender.getCheckedRadioButtonId()==-1
				|| babyBirthText.getText().toString().equals("") || babyWeightText.getText().toString().equals("")
				|| babyHeightText.getText().toString().equals("") || babyPregnancyText.getText().toString().equals("")){
			showAlert(getString(R.string.info_msg));
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
		Log.d("baby_date", m_year + "年" + m_month + "月" + m_day);
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
