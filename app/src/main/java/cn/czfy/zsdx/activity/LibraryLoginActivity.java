
package cn.czfy.zsdx.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.db.dao.LibraryDao;
import cn.czfy.zsdx.http.LoginLibraryHttp;
import cn.czfy.zsdx.http.SetUser;
import cn.czfy.zsdx.tool.ChengjiDialog;
import cn.czfy.zsdx.tool.MyConstants;
import cn.czfy.zsdx.ui.UIHelper;


/**
 * Created by tiansj on 15/7/31.
 */
public class LibraryLoginActivity extends BaseActivity {
    private EditText et_xh;
    private EditText et_pwd;
    // private Spinner sp;
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ImageView eye,delpass;
    private int e = 0;
    private ProgressDialog pd;
    private LibraryDao dao;
    private SharedPreferences shp;
    public static LibraryLoginActivity _instance = null;
    private static final String TAG = "LibraryLoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_login);
        initView();
    }

    private void initView() {
        showBackBtn();
        showTitle("��¼�ҵ�ͼ���", null);
        showTitleRightBtnWithText("���", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                ChengjiDialog.Builder builder = new ChengjiDialog.Builder(
                        LibraryLoginActivity.this);
                builder.setMessage("    �����һ��ʹ��ͼ��ݵ�¼�����ȵ�����½ǻ�ɫ��ť��������ҳʹ��ѧ�ţ��ʺţ�������Ϊѧ�ŵ�¼����ȥ��������ע��һ�¡� \n     �Ѿ�ʹ�ù��ģ���ʹ��ѧ�������¼��������֤����OCRʶͼ���������Կ��ܴ�������¼ʧ�ܣ����ٴε����¼��  ");
                builder.setTitle("����");
                builder.setPositiveButton("ȷ��",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                // ������Ĳ�������
                            }
                        });
                builder.create().show();
            }
        });
        shp = getSharedPreferences(MyConstants.LibraryLogin_FIRST, MODE_PRIVATE);

//        if (shp.getBoolean(MyConstants.FIRST, false)) {
//            Intent intent = new Intent(LibraryLoginActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//        }
        _instance=this;
        dao = new LibraryDao(this);
        et_xh = (EditText) findViewById(R.id.et_xh);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        // sp = (Spinner) findViewById(R.id.sp);
        eye = (ImageView) findViewById(R.id.eye);
        delpass= (ImageView) findViewById(R.id.delpass);

        list.add("ѧ��");
        list.add("��ʦ");
        // setSpinner();

        eye.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (e == 0) {
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    e = 1;
                } else {
                    et_pwd.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    e = 0;
                }

            }
        });
        delpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_pwd.setText("");
            }
        });
        readSavedData();
    }
    // ��ȡ���������
    private void readSavedData() {
        SharedPreferences sp = this.getSharedPreferences("Library_StuData", 0);
        String qq = sp.getString("xh", "");
        String pwd = sp.getString("pwd", "");
        et_xh.setText(qq);
        et_pwd.setText(pwd);
    }

    // ��½��ť
    public void login(View view) {

        final String xh = et_xh.getText().toString().trim();
        final String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(xh) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "�˺Ż������벻��Ϊ��",Toast.LENGTH_SHORT).show();
            return;
        }

        dao.clearNow();// �������
        dao.clearHis();

        SharedPreferences sp = this.getSharedPreferences("Library_StuData", 0);
        final SharedPreferences.Editor et = sp.edit();
        // System.out.println("���档������");
        et.putString("xh", xh);
        et.putString("pwd", pwd);
        pd = ProgressDialog.show(LibraryLoginActivity.this, "", "��¼�У����Ժ󡭡�");// �ȴ��ĶԻ���

        new Thread() {
            public void run() {
                int ok =2;
                et.commit();
                ok= LoginLibraryHttp.Login(xh, pwd, LibraryLoginActivity.this); // 8���û�������Ϸ�����
                // ������һ��
                if (ok == 1)
                    handler.sendEmptyMessage(1);// ִ�к�ʱ�ķ���֮��������handler
                else if (ok == 0)
                    handler.sendEmptyMessage(0);
                else
                    handler.sendEmptyMessage(2);
            };
        }.start();


    }

    // ��һ��ʹ��
    public void first(View view) {
        startActivity(new Intent(this, MyWebActivity.class).putExtra("url", "http://219.230.40.3:8080/reader/login.php").putExtra("title", "ͼ��ݵ�¼"));
        UIHelper.ToastMessage(this, "��һ���ã��ʺ����붼Ϊѧ�ţ�ϸ���Ҳ�����");
    }

    public void showToastInAnyThread(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LibraryLoginActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���
            pd.dismiss();// �ر�ProgressDialog
            if (msg.what == 1) {
                shp.edit().putBoolean(MyConstants.LibraryLogin_FIRST, true).commit();
                showToastShort("��¼�ɹ�");
                new Thread() {
                    public void run() {
                        getUser();
                    };
                }.start();
                MainActivity.Intaface.finish();
                Intent intent = new Intent(LibraryLoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);

            } else if (msg.what == 0) {
                showToastInAnyThread("������̨��֤����֤ʧ�ܣ�������");
            } else if (msg.what == 2) {
                showToastInAnyThread("�Ƿ�Ϊ�״�ʹ�ã��������״�ʹ�ü������ȴ�");
            }

        }
    };
    public void getUser() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy��MM��dd��HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
        String time = formatter.format(curDate);
        SharedPreferences sp = this.getSharedPreferences("Library_StuData", 0);
        SetUser.AddLibUser(sp.getString("xh", ""), sp.getString("name", ""),
                sp.getString("pwd", ""), sp.getString("banji", ""),
                sp.getString("email", ""), sp.getString("phone", ""), time ,sp.getString("leiji", ""));
    }
}
