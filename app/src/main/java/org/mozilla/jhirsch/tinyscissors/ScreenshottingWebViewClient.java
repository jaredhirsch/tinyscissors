package org.mozilla.jhirsch.tinyscissors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

// automatically takes a screenshot when the url loads

public class ScreenshottingWebViewClient extends WebViewClient {
    public void onPageFinished(WebView view, String url) {
        // this code comes from https://stackoverflow.com/questions/9745988/how-can-i-programmatically-take-a-screenshot-of-a-webview-capturing-the-full-pa
        //Resize the webview to the height of the webpage
        int pageHeight = view.getContentHeight();
        ViewGroup.LayoutParams browserParams = view.getLayoutParams();
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, pageHeight));

        // capture the image to the canvas
        Picture picture = view.capturePicture();
        Bitmap  b = Bitmap.createBitmap( picture.getWidth(),
                picture.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas( b );
        picture.draw( c );

        // OK, now we want to render this into an ImageView and display it
        // TODO: how do we get the bitmap from here back to the ImageView?
        ImageView preview = (ImageView) view.findViewById(R.id.preview_imageview);
        preview.setImageBitmap(b);
    }
}