package be.rsmm.newsrsmm;


import android.os.AsyncTask;


import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by thetheos on 12/30/17.
 */


public class ApkUpdateAsyncTask extends AsyncTask<String, Integer, String> {



    @Override
    protected void onPreExecute(){
        Log.d("APKUpdate", "A new version has been found, preparing to install");
        }


    @Override
    protected String doInBackground(String... urls){
        Log.d("APKUpdate","Initialize update");

        String path = config.downloadPath;

        //download the apk from your server and save to sdk card here
        try{
            Log.d("APKUpdate","Try to update");
            URL url = new URL(urls[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            Log.d("APKUpdate", "URL: "+String.valueOf(url));
            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            Log.d("APKUpdate","Could open url");
            OutputStream output = new FileOutputStream(path);
            Log.d("APKUpdate","Could download update" + path);
            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1)
            {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        }catch(Exception e){
            Log.d("APKUpdate","Erorr:", e);
        }

        return path;
    }

    @Override
    protected void onPostExecute(String path)
    {
        sudo("chmod 777 /data/media/0/test/");
        sudo("chmod 777 "+ path);
        sudo("pm install -r "+ path + " && monkey -p be.rsmm.newsrsmm -c android.intent.category.LAUNCHER 1 ");
    }

    public static void sudo(String...strings) {
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s+"\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}