package cn.czfy.zsfy.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.tool.DownLoadManager;
import cn.czfy.zsfy.tool.FeedbackDialog;
import cn.czfy.zsfy.tool.MyConstants;
import cn.czfy.zsfy.tool.UpdataInfo;
import cn.czfy.zsfy.tool.UpdataInfoParser;
import cn.czfy.zsfy.view.CircleImageView;

/**
 * 
 * @author sinyu
 * @description 今日
 */
public class PerInfoActivity extends BaseActivity implements OnClickListener {
	private final String TAG = this.getClass().getName();
	private final int UPDATA_NONEED = 0;
	private final int UPDATA_CLIENT = 1;
	private final int GET_UNDATAINFO_ERROR = 2;
	private final int SDCARD_NOMOUNTED = 3;
	private final int DOWN_ERROR = 4;
	private Button getVersion;
	private LinearLayout tx_feedback;
	private UpdataInfo info;
	private String localVersion;
	private TextView tx_name;
	private TextView tx_class;
	private TextView tx_xh;
	private LinearLayout bt_zx;
	private LinearLayout bt_jc;
	private TextView per_corx;
	private TextView per_xorz;
	private LinearLayout lv_setperinfo;
	private CircleImageView cv;
	private SharedPreferences sp;
	private ProgressDialog pd;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personinfo);
		findViews();
		showBackBtn();
		showTitle("��������",null);
		showTitleRightBtnWithText("�༭", new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(PerInfoActivity.this, SetPerinfoActivity.class));
			}
		});
		sp=this.getSharedPreferences(MyConstants.FIRST, 0);
		
	}
	@SuppressLint("WrongViewCast")
	private void findViews() {
		cv=(CircleImageView)findViewById(R.id.profile_image1);
		tx_name=(TextView)findViewById(R.id.tx_name);
		tx_class=(TextView)findViewById(R.id.tx_class);
		tx_xh=(TextView)findViewById(R.id.tx_xh);
		per_corx=(TextView)findViewById(R.id.per_corx);
		per_xorz=(TextView)findViewById(R.id.per_xorz);
		bt_zx=(LinearLayout)findViewById(R.id.tv_per_zx);
		bt_jc=(LinearLayout)findViewById(R.id.tx_getVersion);
		tx_feedback=(LinearLayout)findViewById(R.id.tx_feedback);

		cv.setOnClickListener(this);
		SharedPreferences sp1 = PerInfoActivity.this.getSharedPreferences(
				"StuData", 0);
		tx_name.setText(sp1.getString("name", ""));
		tx_xh.setText(sp1.getString("xh", ""));
		tx_class.setText(sp1.getString("banji", ""));
		String type=sp1.getString("logintype", "ѧ��");
		if(!type.equals("ѧ��"))
		{
			per_corx.setText("ϵ��:");
			tx_class.setText(sp1.getString("xibu", ""));
			per_xorz.setText("�˺�:");
		}
		String sex=sp1.getString("sex", "��");
		if(sex.equals("��"))
		{
			cv.setImageResource(R.drawable.boy);  //����imageview���ֵ�ͼƬ
		}
		else if(sex.equals("Ů"))
			cv.setImageResource(R.drawable.girl);
		
		
		bt_zx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ע��
				AlertDialog.Builder builder=new AlertDialog.Builder(PerInfoActivity.this);  //�ȵõ�������
		        builder.setTitle("��ʾ"); //���ñ���
		        builder.setMessage("�Ƿ�ȷ���˳�?"); //��������
		        //builder.setIcon(R.mipmap.ic_launcher);//����ͼ�꣬ͼƬid����
		        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                dialog.dismiss(); //�ر�dialog
						sp.edit().putBoolean(MyConstants.FIRST, false).commit();
						MainActivity.Intaface.finish();
		                Intent intent = new Intent(PerInfoActivity.this, LoginActivity.class);
						startActivity(intent);
						PerInfoActivity.this.finish();

		                //Toast.makeText(PerInfoActivity.this, "ȷ��" + which, Toast.LENGTH_SHORT).show();
		            }
		        });
		        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                dialog.dismiss();
		               // Toast.makeText(MainActivity.this, "ȡ��" + which, Toast.LENGTH_SHORT).show();
		            }
		        });
		        //��������������ˣ���������ʾ����
		        builder.create().show();

			}
		});
		bt_jc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//������
				pd = ProgressDialog.show(PerInfoActivity.this, "", "����У����Ժ󡭡�");//�ȴ��ĶԻ���
				try {
					localVersion = getVersionName();
					CheckVersionTask cv = new CheckVersionTask();
					new Thread(cv).start();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		tx_feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//JwxtHttp.Login("12312", "1231212", PerInfoActivity.this);
				FeedbackDialog.Builder builder = new FeedbackDialog.Builder(
						PerInfoActivity.this);
				builder.setMessage("");
				builder.setTitle("����");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								//dialog.dismiss();

								// ������Ĳ�������
							}
						});
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

								// ������Ĳ�������
							}
						});
				builder.create().show();
			}
		});


	}

	private String getVersionName() throws Exception {
		//getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		PackageManager packageManager = PerInfoActivity.this.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(PerInfoActivity.this.getPackageName(),
				0);
		return packInfo.versionName;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.profile_image1:
				startActivity(new Intent(PerInfoActivity.this, SetPerinfoActivity.class));
				break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		refreshInfo();
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	private void refreshInfo(){//ˢ��
		SharedPreferences sp1 = PerInfoActivity.this.getSharedPreferences(
				"StuData", 0);
		tx_name.setText(sp1.getString("name", ""));
		tx_xh.setText(sp1.getString("xh", ""));
		tx_class.setText(sp1.getString("banji", ""));
		String type=sp1.getString("logintype", "ѧ��");
		if(!type.equals("ѧ��"))
		{
			per_corx.setText("ϵ��:");
			tx_class.setText(sp1.getString("xibu", ""));
			per_xorz.setText("�˺�:");
		}
		String sex=sp1.getString("sex", "��");
//		if(sex.equals("��"))
//		{
//			cv.setImageResource(R.drawable.boy);  //����imageview���ֵ�ͼƬ
//		}
//		else if(sex.equals("Ů"))
//			cv.setImageResource(R.drawable.girl);
		String touxiangpath = sp1.getString("touxiangpath", "");
		if (touxiangpath.equals("")) {
			//Ĭ��ͷ��
			if (sex.equals("��")) {
				cv.setImageResource(R.drawable.boy);
			}else
				cv.setImageResource(R.drawable.girl);
		}else
		{
			try
			{//��ȡ����ͷ��
				Uri uri = Uri.fromFile(new File(touxiangpath));
				ContentResolver cr = PerInfoActivity.this.getContentResolver();
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* ��Bitmap�趨��ImageView */
				cv.setImageBitmap(bitmap);
			}catch (Exception e){
				if (sex.equals("��")) {
					cv.setImageResource(R.drawable.boy);
				}else
					cv.setImageResource(R.drawable.girl);
			}
		}
	}

	public class CheckVersionTask implements Runnable {
		InputStream is;
		public void run() {
			try {
				String path = getResources().getString(R.string.url_server);
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    // �ӷ��������һ��������
                	is = conn.getInputStream();
                }
				info = UpdataInfoParser.getUpdataInfo(is);
				if (info.getVersion().equals(localVersion)) {
					Log.i(TAG, "�汾����ͬ");
					Message msg = new Message();
					msg.what = UPDATA_NONEED;
					handler.sendMessage(msg);
					// LoginMain();
				} else {
					Log.i(TAG, "�汾�Ų���ͬ ");
					Message msg = new Message();
					msg.what = UPDATA_CLIENT;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = GET_UNDATAINFO_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case UPDATA_NONEED:
				Toast.makeText(PerInfoActivity.this.getApplicationContext(), "����Ӧ��Ϊ���°汾",
						Toast.LENGTH_SHORT).show();
				handler.sendEmptyMessage(8);
				break;
			case UPDATA_CLIENT:
				 //�Ի���֪ͨ�û���������
				handler.sendEmptyMessage(8);
				showUpdataDialog();
				break;
			case GET_UNDATAINFO_ERROR:
				//��������ʱ
				handler.sendEmptyMessage(8);
	            Toast.makeText(PerInfoActivity.this.getApplicationContext(), "��ȡ������������Ϣʧ��", Toast.LENGTH_LONG).show();
				break;
			case DOWN_ERROR:
				//����apkʧ��
				handler.sendEmptyMessage(8);
	            Toast.makeText(PerInfoActivity.this.getApplicationContext(), "�����°汾ʧ��", Toast.LENGTH_LONG).show();
				break;
			case 8:
				pd.dismiss();// �ر�ProgressDialog
				break;
			}
		}
	};
	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(PerInfoActivity.this);
		builer.setTitle("�汾����");
		builer.setMessage("��鵽�µİ汾�����������������˸��๦�ܣ��Ƿ�������");
		 //����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ   ?
		builer.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "����apk,����");
				downLoadApk();
			}
		});
		builer.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//do sth
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}
	/* 
	 * �ӷ�����������APK 
	 */  
	protected void downLoadApk() {  
	    final ProgressDialog pd;    //�������Ի���
	    pd = new ProgressDialog(PerInfoActivity.this);
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    pd.setMessage("�������ظ���(��λ:KB)");  
	    pd.show();  
	    new Thread(){
	        @Override
	        public void run() {  
	            try {  
	                File file = DownLoadManager.getFileFromServer(info.getUrl(), pd);
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //�������������Ի���  
	            } catch (Exception e) {
	                Message msg = new Message();
	                msg.what = DOWN_ERROR;  
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	  
	//��װapk   
	protected void installApk(File file) {
	    Intent intent = new Intent();
	    //ִ�ж���  
	    intent.setAction(Intent.ACTION_VIEW);
	    //ִ�е���������  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
	    startActivity(intent);  
	}  

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		//getFocus();
	}

//	private void switchFragment(Fragment fragment, String title) {
//		if (PerInfoActivity.this == null) {
//			return;
//		}
//		if (PerInfoActivity.this instanceof MainActivity) {
//			MainActivity fca = (MainActivity) PerInfoActivity.this;
//			fca.switchConent(fragment, title);
//		}
//	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}