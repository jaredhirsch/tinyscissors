package org.mozilla.jhirsch.tinyscissors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;


// automatically takes a screenshot when the url loads

public class ScreenshottingWebViewClient extends WebViewClient {
    private final TextShareActivity launchingActivity;

    public ScreenshottingWebViewClient(TextShareActivity launchingActivity) {
        this.launchingActivity = launchingActivity;
        this.launchingActivity.showWebView();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        // this code comes from https://stackoverflow.com/questions/4302912/android-webview-timeout

        super.onLoadResource(view, url);

        // TODO: add a Timer so we can give up if the page gets stuck.
        if(view.getProgress() != 100) { return; }

        // Resize the webview to the height of the webpage
        // TODO: disabling to see if this is what's wrong
        //int pageHeight = view.getContentHeight();
        //ViewGroup.LayoutParams browserParams = view.getLayoutParams();
        //view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, pageHeight));

        // capture the image to the canvas
        Picture picture = view.capturePicture();
        Bitmap  b;
        b = Bitmap.createBitmap( picture.getWidth(),
                picture.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas( b );
        picture.draw( c );

        this.launchingActivity.setImage(picture, b, c);
    }
}