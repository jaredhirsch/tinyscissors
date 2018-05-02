package org.mozilla.jhirsch.tinyscissors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class TextShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get intent, action, mime type
        Intent intent = getIntent();
        String action = intent.getAction();
        // get mime type so that we can handle both
        // images and URLs later, maybe
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain").equals(type)){
                handleSendText(intent);
            } else if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }
        // TODO: consider handling ACTION_SEND_MULTIPLE if image uploads are added later
        // TODO: handle the "started directly from the home screen" intent
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        Uri uri = Uri.parse(sharedText);
        if (sharedText != null && uri.getScheme().startsWith("http")) {
            // TODO: open the http(s) URL in the WebView
        }
    }
}
