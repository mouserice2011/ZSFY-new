package cn.czfy.zsfy.activity;

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
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.czfy.zsfy.tool.MyConstants;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.db.dao.StudentDao;
import cn.czfy.zsfy.http.CookieHttp;
import cn.czfy.zsfy.tool.ChengjiDialog;


/**
 * Created by tiansj on 15/7/31.
 */
public class LoginActivity extends BaseActivity {
    private EditText et_xh;
    private EditText et_pwd;
    // private Spinner sp;
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ImageView eye,delpass;
    private int e = 0;
    private ProgressDialog pd;
    private StudentDao dao;
    private SharedPreferences shp;
    public static LoginActivity _instance = null;
    private RadioGroup rg;
    private String loginType="ѧ��";
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        showBackBtn();
        showTitle("��¼", null);
        showTitleRightBtnWithText("����", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                ChengjiDialog.Builder builder = new ChengjiDialog.Builder(
                        LoginActivity.this);
                builder.setMessage("    ��Ӧ���ɻ��繤��ϵ�ƶ�Ӧ����ȤС���Ա������Ŀǰ��Ҫ��������Ժѧ���ճ���������д����ƣ������ڴ�ҵļල�뽨���£���Ʒ��Խ��Խ�á� \n     ��¼��ʹ��ѧ�ż��������֤��¼���ɡ�  ");
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
        shp = getSharedPreferences(MyConstants.FIRST, MODE_PRIVATE);
//
//        if (shp.getBoolean(MyConstants.FIRST, false)) {
//            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//        }
        _instance=this;
        dao = new StudentDao(this);
        et_xh = (EditText) findViewById(R.id.et_xh);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        rg = (RadioGroup) findViewById(R.id.rg_logintype);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_bm:
                        loginType="����";
                        break;
                    case R.id.rb_js:
                        loginType="��ʦ";
                        break;
                    case R.id.rb_xs:
                        loginType="ѧ��";
                        break;
                    default:
                        break;
                }
            }
        });
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
        SharedPreferences sp = this.getSharedPreferences("StuData", 0);
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

        dao.clearChengji();// �������
        dao.clearKebiao();
        SharedPreferences sp = this.getSharedPreferences("StuData", 0);
        final SharedPreferences.Editor et = sp.edit();
        // System.out.println("���档������");
        et.putString("xh", xh);
        et.putString("pwd", pwd);
        pd = ProgressDialog.show(LoginActivity.this, "", "��¼�У����Ժ󡭡�");// �ȴ��ĶԻ���

        new Thread() {
            public void run() {
                int ok =2;
                switch (loginType) {
                    case "����":
                    {
                        pd.dismiss();// �ر�ProgressDialog
                        et.putString("logintype", "����");
                        et.commit();
                        showToastInAnyThread("���ŵ�¼��δʵ�֣������֧��");
                        return;
                    }
                    case "��ʦ":
                    {
                        et.putString("logintype", "��ʦ");
                        et.commit();
                        ok= CookieHttp.Login(xh, pwd, LoginActivity.this); // 8���û�������Ϸ�����
                        break;
                    }
                    case "ѧ��":
                    {
                        et.putString("logintype", "ѧ��");
                        et.commit();
                        ok= CookieHttp.Login(xh, pwd, LoginActivity.this); // 8���û�������Ϸ�����
                        break;
                    }
                    default:
                        break;
                }

                // ������һ��
                if (ok == 1)
                    handler.sendEmptyMessage(1);// ִ�к�ʱ�ķ���֮��������handler
                else if (ok == 0)
                    handler.sendEmptyMessage(0);
                else
                    handler.sendEmptyMessage(2);
            };
        }.start();

        shp.edit().putBoolean(MyConstants.FIRST, true).commit();
    }

    // �ÿͰ�ť
    public void fangke(View view) {
        final String xh = et_xh.getText().toString().trim();
        final String pwd = et_pwd.getText().toString().trim();

        dao.clearChengji();// �������
        dao.clearKebiao();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        SharedPreferences sp = this.getSharedPreferences("StuData", 0);
        final SharedPreferences.Editor et = sp.edit();
        // System.out.println("���档������");
        et.putString("xh", xh);
        et.putString("pwd", pwd);
        et.putString("name", "�ÿ�");
        et.putString("sex", "��");
        et.putString("xibu", "  ");
        et.putString("banji", "  ");
        et.putString("logintype", "�ÿ�");
        et.commit();
        shp.edit().putBoolean(MyConstants.FIRST, true).commit();
    }

    public void showToastInAnyThread(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���
            pd.dismiss();// �ر�ProgressDialog
            if (msg.what == 1) {
                Intent intent = new Intent(LoginActivity.this,
                        YzmActivity.class);
                startActivity(intent);

//                overridePendingTransition(android.R.anim.slide_in_left,
//                        android.R.anim.slide_out_right);

            } else if (msg.what == 0) {
                showToastInAnyThread("������ӵ�����Ժ�����");
            } else if (msg.what == 2) {
                showToastInAnyThread("������ӵ�����Ժ�����");
            }

        }
    };

}
