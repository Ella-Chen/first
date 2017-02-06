package example.com.qiyicom;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 红包大战
 */

public class Redmoney extends Activity {
    private WebView webview01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redmoney);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);



        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







        String urlString = "http://121.43.234.2/QYKG//LJWY/UR00/hbdz.html?f=android";
        webview01 = (WebView) findViewById(R.id.webview01);
        webview01.getSettings().setAllowFileAccess(true);
        webview01.getSettings().setAllowContentAccess(true);
        webview01.getSettings().setAppCacheEnabled(true);
        webview01.getSettings().setJavaScriptEnabled(true);
        webview01.loadUrl(urlString);


        WebSettings webSettings = webview01.getSettings();
        webSettings.setDomStorageEnabled(true);

        //在js中调用本地java方法
        webview01.addJavascriptInterface(new JsInterface(getApplicationContext()), "AndroidWebView");

        //添加客户端支持
        webview01.setWebChromeClient(new WebChromeClient());



    }

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在webview的js中执行showInfoFromJs会触发安卓的此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }

}
