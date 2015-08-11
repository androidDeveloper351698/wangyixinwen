package com.meetu.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.meetu.R;
import com.meetu.R.id;
import com.meetu.R.layout;
import com.meetu.R.menu;
import com.meetu.tools.BitmapCut;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

public class Shezhigerenxinxi extends Activity implements OnClickListener{
	private ImageView ivTouxiang;
	private EditText sex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shezhigerenxinxi);
		initView();
		String p=getFilesDir().toString();
		Log.d("lucifer",p);
	}
	
	

	
	private void initView(){
		ivTouxiang=(ImageView)super.findViewById(R.id.iv_touxiang_shezhigerenxinxi);
		Bitmap head=readHead();
		if(head!=null){
			ivTouxiang.setImageBitmap(head);
		}
		ivTouxiang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});
		sex=(EditText)super.findViewById(R.id.et_sex_shezhigerenxinxi);
		sex.setOnClickListener(this);
		
		
	}
	private void showDialog(){
		final  AlertDialog portraidlg=new AlertDialog.Builder(this).create();
		portraidlg.show();
		Window win=portraidlg.getWindow();
		win.setContentView(R.layout.dialog_show_photo);
		RadioButton portrait_native=(RadioButton)win.findViewById(R.id.Portrait_native);
		portrait_native.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1=new Intent(Intent.ACTION_PICK,null);
				intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent1, 11);
				portraidlg.dismiss();
			}
		});
		RadioButton portrait_take=(RadioButton)win.findViewById(R.id.Portrait_take);
		portrait_take.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//��������ͷ
				Intent intent2=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
						"/user_header.png")));
				startActivityForResult(intent2, 22);
				portraidlg.dismiss();
			}
		});
		View viewTop=win.findViewById(R.id.view_top_dialog_sethead);
		View viewBottom=win.findViewById(R.id.view_bottom_dialog_sethead);
		//���dialog�ⲿ���ر�dialog
				viewTop.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						portraidlg.dismiss();
					}
				});
				viewBottom.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						portraidlg.dismiss();
					}
				});
	
		
		
	}
	private Bitmap headerPortait;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 11:
			if(resultCode==this.RESULT_OK){
				cropPhoto(data.getData());//�ü�ͼƬ
			}
			break ;
		case 22:
			if(resultCode==this.RESULT_OK){
				File temp=new File(Environment.getExternalStorageDirectory()
						+ "/user_header.png");
				cropPhoto(Uri.fromFile(temp));//�ü�ͼƬ
				
			}
			
			break;
		case 33:
			if(data!=null){
				Bundle extras=data.getExtras();
				headerPortait=extras.getParcelable("data");
				headerPortait=BitmapCut.toRoundBitmap(headerPortait);
				if(headerPortait!=null){
					saveHeadImg(headerPortait);
					ivTouxiang.setImageBitmap(headerPortait);
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	public void saveHeadImg(Bitmap head){
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/user_header.png"));
			head.compress(CompressFormat.PNG, 100, fos);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(fos!=null)fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shezhigerenxinxi, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.et_sex_shezhigerenxinxi:
			initPopupWindow();
			popupWindow.showAsDropDown(v, 0, 0);
			
			break;

		default:
			break;
		}
	}
	
	public Bitmap readHead(){
		String file=Environment.getExternalStorageDirectory()+"/user_header.png";
		return BitmapFactory.decodeFile(file);
	}
	
	/**
     * �������յĲü�����
     * @param uri
     */
	
	public void cropPhoto(Uri uri){
		//�������յĲü�����
		Intent intent=new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		//aspectX aspectY �ǿ�͸�ı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// // outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("return-data",true);
		startActivityForResult(intent,33);
	}
	private PopupWindow popupWindow;
	private void initPopupWindow() {
		if (popupWindow == null) {
			View view = LayoutInflater.from(this).inflate(R.layout.dialog__sex_selector,
					null);		
			popupWindow = new PopupWindow(view,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			// �������
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			ColorDrawable colorDrawable = new ColorDrawable();
			popupWindow.setBackgroundDrawable(colorDrawable);
		//	tvTitle=(TextView)view.findViewById(R.id.tvcolectList);
		}
		
	}


}
