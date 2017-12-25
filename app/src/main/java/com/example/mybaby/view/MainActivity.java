package com.example.mybaby.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.mybaby.R;

public class MainActivity extends AppCompatActivity {

    private static final String URL="http://wpa.qq.com/msgrd?v=3&uin=174668774&site=qq&menu=yes";
    private WebView mWebView;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn= (Button) findViewById(R.id.btn);
        mWebView= (WebView) findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
                System.out.println("-------------"+url);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return false;
            }
        });





        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String url="mqqwpa://im/chat?chat_type=crm&uin=800095555&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
//                String url="mqqwpa://im/chat?chat_type=crm&uin=938194084&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                mWebView.loadUrl("http://wpa.b.qq.com/cgi/wpa.php?ln=2&uin=4008031206");

            }
        });
    }

}
