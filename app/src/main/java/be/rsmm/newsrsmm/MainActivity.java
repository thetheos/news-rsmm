package be.rsmm.newsrsmm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

//README
/*************************
-grable.build
--VERSION CODE: change the version code in the build.gradle file.

 -config:
 --APK DOWNLOAD URL: apkUrl String in the config java class
 --JSON DOWNLOAD URL: use to check version. jsonUrl String in the config java class
 --LOCAL DOWNLOAD PATH: where the apk is gonna be download. downloadPath String in the config java class


**************************/

public class MainActivity extends Activity {


    private WebView wview;
    private Runnable t;


    private final static int INTERVAL = 1000 * 60 * 20; //20 minutes
    Handler mHandler = new Handler();

    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            //take one argument: the current version
            new getVersionJsonAsyncTask().execute(getVersion());
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            wview.loadUrl(config.urlInternetRsmm);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Remove notification bar

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        wview = (WebView) this.findViewById(R.id.Wv1);
        wview.getSettings().setJavaScriptEnabled(true);
        wview.clearHistory();
        wview.clearFormData();
        wview.clearCache(true);

        WebSettings webSettings = wview.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        wview.setWebViewClient(new WebViewClient());
        wview.loadUrl(config.urlInternetRsmm);

        //start repeating task;
        mHandlerTask.run();

    }

    protected Integer getVersion(){
        int currentVersion = 0;
        try{
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersion = pInfo.versionCode;
            Log.d("MAINDebug", "current version"+String.valueOf(currentVersion));
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersion;
    }



    public void showMainsite(View v){
        wview.loadUrl("about:blank");
        wview.loadUrl(config.urlInternetRsmm);
    }

    public void showResto(View v){
        wview.loadUrl("about:blank");
        wview.loadUrl(config.urlPdfMenu);
    }

    public void showActivites(View v){
        wview.loadUrl("about:blank");
        wview.loadUrl(config.urlPdfActivites);
    }

    public void showGoogle(View v){
        wview.loadUrl("about:blank");
        wview.loadUrl(config.urlInternet);
    }

    public void backkey(View v){
        if (wview.canGoBack()){
            wview.goBack();
        }
    }

    public void showCamera(){
        wview.loadUrl("http://192.168.50.163:8090/cam2.mjpg");
        timerHandler.postDelayed(timerRunnable,60000);
    }

    public void showCamera(View v){
        showCamera();
    }


}





