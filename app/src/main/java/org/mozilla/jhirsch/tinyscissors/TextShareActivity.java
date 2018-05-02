package org.mozilla.jhirsch.tinyscissors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TextShareActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get intent, action, mime type
        Intent intent = getIntent();
        String action = intent.getAction();
        // get mime type so that we can handle both
        // images and URLs later, maybe
        String type = intent.getType();

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain").equals(type)){
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                mWebView.loadUrl(sharedText); // TODO check it's a valid URL

            }
        }
        // TODO: consider handling images, and ACTION_SEND_MULTIPLE if image uploads are added later
    }

}
