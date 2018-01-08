package be.rsmm.newsrsmm;

import android.os.AsyncTask;
import android.support.v4.app.AppLaunchChecker;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thetheos on 12/30/17.
 */

public class getVersionJsonAsyncTask extends AsyncTask<Integer, String, Void> {
    public getVersionJsonAsyncTask(){}


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        Log.d("JSONUpdate", "JsonData Downloading");
    }

    @Override
    protected Void doInBackground(Integer... currentVersionNbr){

        publishProgress("sending");

        HttpHandler sh = new HttpHandler();
        // Making request to a url and getting the response
        String url = config.jsonUrl;
        String jsonStr = sh.makeServiceCall(url);
        Log.d("JSONUpdate", "Response from url: " + jsonStr);

        if(jsonStr != null){
            try{
                JSONObject jsonObj = new JSONObject(jsonStr);
                Integer serverVersionNbr = Integer.parseInt(jsonObj.getString("version"));
                config.apkUrl = jsonObj.getString("urlApk");
                config.urlPdfMenu = jsonObj.getString("urlPdfMenu");
                config.urlPdfActivites = jsonObj.getString("urlPdfActivites");
                config.urlInternet = jsonObj.getString("urlInternet");
                config.urlInternetRsmm = jsonObj.getString("urlInternetRsmm");
                config.urlCamera = jsonObj.getString("urlCamera");

                Log.d("JSONUpdate","server version:" + String.valueOf(serverVersionNbr));
                if(currentVersionNbr[0] < serverVersionNbr){
                    String downloadUrl = config.apkUrl;
                    new ApkUpdateAsyncTask().execute(downloadUrl);
                }
                else {
                    Log.d("JSONUpdate", "Version is up to date");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

      return null;
    }

    @Override
    protected void onProgressUpdate(String... i){
        //super.onProgressUpdate(i);
    }

}
