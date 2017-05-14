
package cn.czfy.zsdx.activity;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.db.dao.StudentDao;
import cn.czfy.zsdx.fragment.KebiaoFragment;
import cn.czfy.zsdx.fragment.LibraryPagerFragment;
import cn.czfy.zsdx.fragment.MainPagerFragment;
import cn.czfy.zsdx.fragment.MemberFragment;
import cn.czfy.zsdx.http.MessageHttp;
import cn.czfy.zsdx.tool.CustomDialog;
import cn.czfy.zsdx.tool.DateUtils;
import cn.czfy.zsdx.tool.ListCache.SaveBookData;
import cn.czfy.zsdx.tool.ListCache.SaveBookRecommend;
import cn.czfy.zsdx.tool.ListCache.SaveFoundLostList;
import cn.czfy.zsdx.tool.ListCache.SaveWeixinArticle;
import cn.czfy.zsdx.tool.MyConstants;
import cn.czfy.zsdx.tool.checkUpdateAPK;
import cn.czfy.zsdx.ui.UIHelper;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;
    SharedPreferences setting;
    private RadioGroup group;
    public static MainActivity Intaface;
    private KebiaoFragment kebiaoFragment;
    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;
    private TextView tv_newmsg;
    StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intaface = this;
        setting = getSharedPreferences("SHARE_APP_TAG", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);

        dao = new StudentDao(this);
        kebiaoFragment = new KebiaoFragment();
        fragmentManager = getSupportFragmentManager();
        initData(savedInstanceState);
        initView();
        jianchaMsg();
        checkUpdateAPK checkUpdateAPK = new checkUpdateAPK(MainActivity.this);
        checkUpdateAPK.jianchagengxin();

        String packageName = "com.czfy.zsfy";
        //Uninstall(packageName);
    }
    /**
     * ж��ָ��������Ӧ��
     *
     * @param packageName
     */
    private void Uninstall(final String packageName) {
        if (checkApplication(packageName)) {
            AlertDialog.Builder builer = new AlertDialog.Builder(this);
            builer.setTitle("��鵽�ɰ汾");
            builer.setMessage("��ã����θ�����ȫ�¸��£������˰�������鵽�����ֻ����оɰ�����ϴ�ѧAPP������ж�ؾɰ汾���Ƿ�ж�أ�");
            // ����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ ?
            builer.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Uri packageURI = Uri.parse("package:" + packageName);
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(packageURI);
                    startActivity(intent);
                }
            });
            builer.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    // do sth
                }
            });
            AlertDialog dialog = builer.create();
            dialog.show();

        }
    }

    /**
     * �жϸð�����Ӧ���Ƿ�װ
     *
     * @param packageName
     * @return
     */
    public boolean checkApplication(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "KebiaoFragment", "InterestFragment", "MemberFragment"));
        currIndex = 0;
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void initView() {
        tv_newmsg = (TextView) findViewById(R.id.textUnreadLabel);
        Boolean user_msg = setting.getBoolean("user_Msg", true);
        if (!user_msg) {
            //Toast.makeText(this, ""+user_msg, 0).show();
            tv_newmsg.setVisibility(View.VISIBLE);
        } else {
            tv_newmsg.setVisibility(View.GONE);
        }
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        setTitleHome();
                        currIndex = 0;
                        break;
                    case R.id.foot_bar_im:
                        setTitleKebiao();
                        currIndex = 1;
                        break;
                    case R.id.foot_bar_interest:
                        setTitleLibrary();
                        showTitleRightBtnWithText("����", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, MyWebActivity.class).putExtra("url", "http://lib.cztgi.edu.cn/").putExtra("title", "ͼ���"));
                            }
                        });
                        currIndex = 2;
                        break;
                    case R.id.main_footbar_user:
                        currIndex = 3;
                        setTitleMember();
                        String type = "��¼";
                        showTitleLeftBtnWithText("����", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, MyWebActivity.class).putExtra("url", "http://app.sinyu1012.cn/help.html").putExtra("title", "����"));
                            }
                        });
                        String logintype = MainActivity.this.getSharedPreferences("StuData", 0).getString("logintype", "");
                        if (!(logintype.equals("") || logintype.equals("�ÿ�"))) {
                            type = "����";
                        }
                        showTitleRightBtnWithText(type, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UIHelper.showLogin(MainActivity.this);
                            }
                        });
                        break;
                    default:
                        break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    private void showFragment() {
        if (currIndex == 3) {
            if (!this.getSharedPreferences(MyConstants.FIRST, 0).getBoolean(MyConstants.FIRST, false)) {
                UIHelper.showLogin(MainActivity.this);
            }
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new MainPagerFragment();
            case 1:
                return kebiaoFragment;
            case 2:
                return new LibraryPagerFragment();
            case 3:
                return new MemberFragment();
            default:
                return null;
        }
    }

    public void showTitleLeftBtnWithText(String resource, View.OnClickListener clickListener) {
        TextView Left_tv = (TextView) findViewById(R.id.Left_tv);
        Left_tv.setVisibility(View.VISIBLE);
        Left_tv.setText(resource);
        Left_tv.setOnClickListener(clickListener);
    }

    public void showTitleRightBtnWithText(String text, View.OnClickListener clickListener) {
        TextView Right_tv = (TextView) this.findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.VISIBLE);
        Right_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        Right_tv.setText(text);
        Right_tv.setBackgroundDrawable(null);
        Right_tv.setOnClickListener(clickListener);
    }

    private void setTitleKebiao() {
        TextView Left_tv = (TextView) findViewById(R.id.Left_tv);
        Left_tv.setVisibility(View.GONE);
        Spinner head_sp = (Spinner) this.findViewById(R.id.head_sp);
        head_sp.setVisibility(View.VISIBLE);
        setSpinner(head_sp);
        TextView Right_tv = (TextView) this.findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.GONE);

    }

    private void setTitleHome() {
        TextView Right_tv = (TextView) this.findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.GONE);
        Spinner head_sp = (Spinner) this.findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView Left_tv = (TextView) findViewById(R.id.Left_tv);
        Left_tv.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("FY���ϴ�ѧ");
    }

    private void setTitleLibrary() {
        TextView Right_tv = (TextView) this.findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.GONE);
        Spinner head_sp = (Spinner) this.findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView Left_tv = (TextView) findViewById(R.id.Left_tv);
        Left_tv.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("ͼ������");
    }

    private void setTitleMember() {
        Spinner head_sp = (Spinner) this.findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("��");
    }

    // �����Ϣ����
    public void jianchaMsg() {

        new Thread() {
            @Override
            public void run() {
                try {
                    // TODO Auto-generated method stub
                    String result = MessageHttp.Message();
                    if (!result.equals("0")) {
                        try {
                            JSONArray jsarr = new JSONArray(result);
                            List<cn.czfy.zsdx.db.dao.Message> infos = dao.findMsg();
                            System.out.println(jsarr.length() + "----------" + infos.size());
                            if (jsarr.length() != infos.size())//�и���
                            {
                                dao.clearMSG();
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                                for (int i = 0; i < jsarr.length(); i++) {
                                    JSONObject json = jsarr.getJSONObject(i);
                                    String type = json.getString("type");
                                    String title = json.getString("title");
                                    String content = json.getString("content");
                                    String time = json.getString("time");
                                    dao.addMsg(type.trim(), title.trim(), content.trim(), time.trim());
                                }
                                JSONObject json = jsarr.getJSONObject(jsarr.length() - 1);
                                Message msg1 = new Message();
                                msg1.obj = json.getString("content");
                                msg1.what = 2;
                                handler.sendMessage(msg1);
//                                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
//                                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//                                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                                Notification notification = new NotificationCompat.Builder(MainActivity.this)
//                                        .setContentTitle("���µ���Ϣ")
//                                        .setContentText(json.getString("title"))
//                                        .setWhen(System.currentTimeMillis())
//                                        .setSmallIcon(R.drawable.czfy)
//                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.czfy))
//                                        .setContentIntent(pi)
//                                        .setAutoCancel(true)
//                                        .setPriority(NotificationCompat.PRIORITY_MAX)
//                                        .build();
//                                manager.notify(1, notification);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        //û������
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                SharedPreferences.Editor et = setting.edit();
                // System.out.println("���档������");
                et.putBoolean("user_Msg", false);
                et.commit();
                tv_newmsg.setVisibility(View.VISIBLE);

            } else if (msg.what == 2) {
                showdia("", msg.obj.toString());
            }
        }
    };

    public void showdia(String title, String str) {
        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
        builder.setMessage(str.trim());
        builder.setTitle("����Ϣ");
        builder.setPositiveButton("ȷ��",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();

                    }
                });
        builder.create().show();
    }
    // ����һ������������ʶ�Ƿ��˳�
    private static boolean isExit = false;
    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            SaveBookRecommend.clear();
            SaveWeixinArticle.clear();
            SaveFoundLostList.clear();
            SaveBookData.clear();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�Ӧ��",
                    Toast.LENGTH_SHORT).show();
            // ����handler�ӳٷ��͸���״̬��Ϣ
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {

            Log.e(TAG, "exit application");

            this.finish();
        }
    }
    /**
     * �α��������
     *
     * @param head_sp
     */
    public void setSpinner(Spinner head_sp) {
        List<String> list = new ArrayList<String>();
        list.add("�鿴ȫ��");
        int dangqianzhou = DateUtils.getWeek();
        SharedPreferences sp = this.getSharedPreferences("StuData", 0);
        SharedPreferences.Editor ed = sp.edit();
        String fzvip = sp.getString("fzvip", "0");
        if (!fzvip.equals("1")) {
            showTitleLeftBtnWithText("����", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showdia();
                }
            });
        }
        if (sp.getBoolean("FirFZ", true)) {
            if (!fzvip.equals("1")) {
                showdia();
            }
            ed.putBoolean("FirFZ", false);
            ed.commit();
        }

        Log.d(TAG, "onCreateView: " + fzvip);
        if (fzvip.equals("1")) {
            // �����ܴ�
            for (int i = 1; i <= 26; i++) {
                if (i == dangqianzhou) {
                    list.add("��" + i + "�ܣ����ܣ�");
                } else
                    list.add("��" + i + "��");
            }
        } else {
            list.add("��������");

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.myspinner, list);

        // new ArrayAdapter<String>(this,
        // android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(R.layout.myspinner);
        head_sp.setAdapter(adapter);
        if (fzvip.equals("1")) {
            //head_sp.setSelection(dangqianzhou);// Ĭ�ϵ�ǰ��
        } else
            dangqianzhou = 0;
        head_sp.setSelection(dangqianzhou);// Ĭ�ϵ�ǰ��
        head_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // ����ѡ���
                kebiaoFragment.handler.sendEmptyMessage(arg2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void showdia() {
        String str = "  ͬѧ����ã�\n   Ϊ�˲��ٳ��ַ��ܵ��µĲ���α������ʾ�������Ʒ����С�" +
                "���迪ͨ���Ⱥ˶��Լ����˿α�������ͨ��֧����ת�˹��ܣ�ת1ԪǮ�ڲ�ѵ�֧�����˺ţ�1341156974@qq.com������ע�Լ���ѧ�ţ�ת�˺���ȴ�һ��ʱ�䣬��̨�ἰʱ����";
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle("�����ڲ⹫��");
        builder.setPositiveButton("ȷ��������",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        ClipboardManager cmb = (ClipboardManager) MainActivity.this.getSystemService(CLIPBOARD_SERVICE);
                        //cmb.setText("");
                        cmb.setText("1341156974@qq.com");
                        Toast.makeText(MainActivity.this, "�����˺ųɹ�", Toast.LENGTH_LONG).show();
                        // ������Ĳ�������
                    }
                });
        builder.create().show();
    }
}
