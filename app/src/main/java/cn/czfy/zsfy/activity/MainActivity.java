
package cn.czfy.zsfy.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.fragment.KebiaoFragment;
import cn.czfy.zsfy.fragment.LibraryFragment;
import cn.czfy.zsfy.fragment.MainPagerFragment;
import cn.czfy.zsfy.fragment.MemberFragment;
import cn.czfy.zsfy.tool.DateUtils;
import cn.czfy.zsfy.ui.UIHelper;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    private RadioGroup group;
    public static MainActivity Intaface;
    private KebiaoFragment kebiaoFragment;
    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intaface=this;
        fragmentManager = getSupportFragmentManager();
        initData(savedInstanceState);
        kebiaoFragment = new KebiaoFragment();
        initView();
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
                        currIndex = 2;
                        break;
                    case R.id.main_footbar_user:
                        currIndex = 3;
                        setTitleMember();
                        showTitleRightBtnWithText("换号", new View.OnClickListener() {
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
//        if (currIndex == 3) {
//            UIHelper.showLogin(MainActivity.this);
//        }

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
                return new LibraryFragment();
            case 3:
                return new MemberFragment();
            default:
                return null;
        }
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
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("掌上大学-FY");
    }
    private void setTitleLibrary() {
        TextView Right_tv = (TextView) this.findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.GONE);
        Spinner head_sp = (Spinner) this.findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("馆藏查询");
    }

    private void setTitleMember() {
        Spinner head_sp = (Spinner) this.findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("我");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 课表分周下拉
     *
     * @param head_sp
     */
    public void setSpinner(Spinner head_sp) {
        List<String> list = new ArrayList<String>();
        list.add("查看全部");
        int dangqianzhou = DateUtils.getWeek();
        SharedPreferences sp = this.getSharedPreferences("StuData", 0);
        SharedPreferences.Editor ed = sp.edit();
        if (sp.getBoolean("FirFZ", true)) {
            head_sp.setSelection(1);
            ed.putBoolean("FirFZ", false);
        }
        String fzvip = sp.getString("fzvip", "0");
        Log.d(TAG, "onCreateView: " + fzvip);
        if (fzvip.equals("1")) {
            // 控制周次
            for (int i = 1; i <= 26; i++) {
                if (i == dangqianzhou) {
                    list.add("第" + i + "周（本周）");
                } else
                    list.add("第" + i + "周");
            }
        } else {
            list.add("分周限制");

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.myspinner, list);

        // new ArrayAdapter<String>(this,
        // android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(R.layout.myspinner);
        head_sp.setAdapter(adapter);
        if (fzvip.equals("1")) {
            //head_sp.setSelection(dangqianzhou);// 默认当前周
        } else
            dangqianzhou = 0;
        head_sp.setSelection(dangqianzhou);// 默认当前周
        head_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 下拉选择的
                kebiaoFragment.handler.sendEmptyMessage(arg2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

}
