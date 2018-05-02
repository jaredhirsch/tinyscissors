package org.mozilla.jhirsch.tinyscissors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TextShareActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        mWebView = (WebView) this.<View>findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.enableSlowWholeDocumentDraw();
        }
        mWebView.setWebViewClient(new ScreenshottingWebViewClient());


        // get intent, action, mime type
        Intent intent = getIntent();
        String action = intent.getAction();
        // get mime type so that we can handle both
        // images and URLs later, maybe
        String type = intent.getType();


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (("text/plain").equals(type)){
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                mWebView.loadUrl(sharedText); // TODO check it's a valid URL
                mWebView.evaluateJavascript("(function() { " +
                        "var canvas = document.createElementNS(\"http://www.w3.org/1999/xhtml\", \"canvas\");" +
                        "var ctx = canvas.getContext(\"2d\");" +
                        " })();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d("LogName", s);
                    }
                });
            }
        }
        // TODO: consider handling images, and ACTION_SEND_MULTIPLE if image uploads are added later
    }

}
