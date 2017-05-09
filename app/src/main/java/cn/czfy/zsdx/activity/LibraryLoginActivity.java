
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
        showTitle("登录我的图书馆", null);
        showTitleRightBtnWithText("相关", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                ChengjiDialog.Builder builder = new ChengjiDialog.Builder(
                        LibraryLoginActivity.this);
                builder.setMessage("    如果第一次使用图书馆登录，请先点击右下角灰色按钮，进入网页使用学号（帐号），密码为学号登录，进去输入姓名注册一下。 \n     已经使用过的，请使用学号密码登录。由于验证码是OCR识图技术，所以可能存在误差，登录失败，可再次点击登录。  ");
                builder.setTitle("关于");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                // 设置你的操作事项
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

        list.add("学生");
        list.add("教师");
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
    // 读取保存的数据
    private void readSavedData() {
        SharedPreferences sp = this.getSharedPreferences("Library_StuData", 0);
        String qq = sp.getString("xh", "");
        String pwd = sp.getString("pwd", "");
        et_xh.setText(qq);
        et_pwd.setText(pwd);
    }

    // 登陆按钮
    public void login(View view) {

        final String xh = et_xh.getText().toString().trim();
        final String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(xh) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "账号或者密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        dao.clearNow();// 清除数据
        dao.clearHis();

        SharedPreferences sp = this.getSharedPreferences("Library_StuData", 0);
        final SharedPreferences.Editor et = sp.edit();
        // System.out.println("保存。。。。");
        et.putString("xh", xh);
        et.putString("pwd", pwd);
        pd = ProgressDialog.show(LibraryLoginActivity.this, "", "登录中，请稍后……");// 等待的对话框

        new Thread() {
            public void run() {
                int ok =2;
                et.commit();
                ok= LoginLibraryHttp.Login(xh, pwd, LibraryLoginActivity.this); // 8秒后没有连接上服务器
                // 继续下一步
                if (ok == 1)
                    handler.sendEmptyMessage(1);// 执行耗时的方法之后发送消给handler
                else if (ok == 0)
                    handler.sendEmptyMessage(0);
                else
                    handler.sendEmptyMessage(2);
            };
        }.start();


    }

    // 第一次使用
    public void first(View view) {
        startActivity(new Intent(this, MyWebActivity.class).putExtra("url", "http://219.230.40.3:8080/reader/login.php").putExtra("title", "图书馆登录"));
        UIHelper.ToastMessage(this, "第一次用，帐号密码都为学号，细读右侧文字");
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
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
            pd.dismiss();// 关闭ProgressDialog
            if (msg.what == 1) {
                shp.edit().putBoolean(MyConstants.LibraryLogin_FIRST, true).commit();
                showToastShort("登录成功");
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
                showToastInAnyThread("密码或后台验证码验证失败，请重试");
            } else if (msg.what == 2) {
                showToastInAnyThread("是否为首次使用，是请点击首次使用激活，否请等待");
            }

        }
    };
    public void getUser() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String time = formatter.format(curDate);
        SharedPreferences sp = this.getSharedPreferences("Library_StuData", 0);
        SetUser.AddLibUser(sp.getString("xh", ""), sp.getString("name", ""),
                sp.getString("pwd", ""), sp.getString("banji", ""),
                sp.getString("email", ""), sp.getString("phone", ""), time ,sp.getString("leiji", ""));
    }
}
