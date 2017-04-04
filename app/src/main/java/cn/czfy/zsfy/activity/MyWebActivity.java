package cn.czfy.zsfy.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import cn.czfy.zsfy.R;


public class MyWebActivity extends BaseActivity {

    com.tencent.smtt.sdk.WebView tbsContent;
    private String url = "",title;
    private ProgressBar pg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web);
        showBackBtn();
        url=this.getIntent().getStringExtra("url");
        title=this.getIntent().getStringExtra("title");
        showTitle(title, null);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    private void initView() {
        pg1 = (ProgressBar) findViewById(R.id.progressBar1);
        tbsContent = (com.tencent.smtt.sdk.WebView) findViewById(R.id.tbsContent);
        tbsContent.loadUrl(url);
        String title;
        WebSettings webSettings = tbsContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        tbsContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                showTitle(view.getTitle(), null);
                return true;
            }
        });
        showTitleRightBtnWithText("����", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                       tbsContent.getUrl());
                shareIntent.setType("text/plain");
                // ���÷����б�ı��⣬����ÿ�ζ���ʾ�����б�
                startActivity(Intent.createChooser(shareIntent, "����"));
            }
        });
        tbsContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO �Զ����ɵķ������

                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//��������ҳ��������ʧ
                } else {
                    pg1.setVisibility(View.VISIBLE);//��ʼ������ҳʱ��ʾ������
                    pg1.setProgress(newProgress);//���ý���ֵ
                }

            }
        });
//֧��javascript
        tbsContent.getSettings().setJavaScriptEnabled(true);
// ���ÿ���֧������
        tbsContent.getSettings().setSupportZoom(true);
// ���ó������Ź���
        tbsContent.getSettings().setBuiltInZoomControls(true);
//�������������
        tbsContent.getSettings().setUseWideViewPort(true);
//����Ӧ��Ļ
        tbsContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        tbsContent.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && tbsContent.canGoBack()) {
            tbsContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}