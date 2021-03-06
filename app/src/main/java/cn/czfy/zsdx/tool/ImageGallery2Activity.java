package cn.czfy.zsdx.tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.activity.BaseActivity;
import cn.czfy.zsdx.activity.MyWebActivity;
import cn.czfy.zsdx.ui.photoview.PhotoViewAdapter;

/**
 * Created by tiansj on 15/8/6.
 */
public class ImageGallery2Activity extends BaseActivity {

    private int position;
    private List<String> imgUrls; //图片列表
    private TextView headTitle;
    private Button headBackBtn;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_gallery);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        String type=intent.getStringExtra("type");
        if (type.equals("imgs")){
            showTitleRightBtnWithText("鸣谢", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ImageGallery2Activity.this, MyWebActivity.class).putExtra("url","http://app2.sinyu1012.cn/Thanks.html").putExtra("title","感谢你们的支持"));
                }
            });
        }
        imgUrls = intent.getStringArrayListExtra("images");
        if(imgUrls == null) {
            imgUrls = new ArrayList<>();
        }
        initView();
        initViewEvent();
        initGalleryViewPager();
    }

    private void initView() {
        headTitle = (TextView)findViewById(R.id.textHeadTitle);
        headTitle.setText("1/" + imgUrls.size());
        headBackBtn = (Button)findViewById(R.id.btnBack);
        headBackBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViewEvent() {
        headBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initGalleryViewPager() {
        PhotoViewAdapter pagerAdapter = new PhotoViewAdapter(this, imgUrls);
        pagerAdapter.setOnItemChangeListener(new PhotoViewAdapter.OnItemChangeListener() {
            int len = imgUrls.size();
            @Override
            public void onItemChange(int currentPosition) {
                headTitle.setText((currentPosition+1) + "/" + len);
            }
        });
        mViewPager = (ViewPager)findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(position);
    }

}
