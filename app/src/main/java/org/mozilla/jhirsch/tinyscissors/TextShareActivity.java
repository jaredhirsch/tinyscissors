package org.mozilla.jhirsch.tinyscissors;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TextShareActivity extends Activity {

    private WebView webView;
    private ImageView imageView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        // TODO: do we really need this ref?
        linearLayout = (LinearLayout) findViewById(R.id.main_layout);

        imageView = (ImageView) this.<View>findViewById(R.id.preview_imageview);
        // TODO: hide the imageView at first
        imageView.setVisibility(View.GONE);

        webView = (WebView) this.<View>findViewById(R.id.activity_main_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.enableSlowWholeDocumentDraw();
        }
        // pass a ref into the web client, so we can get the picture back
        webView.setWebViewClient(new ScreenshottingWebViewClient(this));

        // parse the Intent info and take action
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (("text/plain").equals(type)){
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                webView.loadUrl(sharedText); // TODO check it's a valid URL
            }
            // TODO (future): if the mime type is an image, load it straight into the ImageView
        }
        // TODO (future): handle multiple image intent?
    }

    public void setImage(Picture picture, Bitmap b, Canvas c) {
        // when the web view client is done, it returns the image here.
        // 1. hide the webview
        // 2. render the image into an imageview
        // 3. show the imageview / hide the webview
        // TODO (future): should the webview even be visible? should we have a spinner overlay on top of it?
        // TODO (future): yes, it shoudl be visible if we want users to pick regions. not otherwise.
        // TODO (future): shoudl we just have a three step process, where step 1 is a webview, step 2 is an image preview,
        // TODO (future): and step 3 is a share sheet or something? then, sending an image could jump to step 2

        // OK, now we want to render this into an ImageView and display it
        imageView.setImageBitmap(b);

        webView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

    }

    public void showWebView() {
        // TODO is this wrong?
        webView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
    }
}
