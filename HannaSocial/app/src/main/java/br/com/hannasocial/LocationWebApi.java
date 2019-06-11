package br.com.hannasocial;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.AsyncTask;
import android.util.Log;

import static java.util.Calendar.*;

public class LocationWebApi extends AsyncTask<LocationDevice, Void, Void>        {
    /*
    * DNS IPV4: ec2-18-222-248-86.us-east-2.compute.amazonaws.com
    * LOCAL: "http://192.168.11.3:3003/api/locationDevices"
    * */
    public static final String server = "http://ec2-18-222-248-86.us-east-2.compute.amazonaws.com:3003/api/locationDevices";
    private static final String mWebApi = server ;
    private Context mContext;
    private LocationDevice _locationdevice;

    @Override
    protected Void doInBackground(LocationDevice... locationDevices) {
        canyouhelpme(_locationdevice);
        return null;
    }

    public LocationWebApi(Context ctx, LocationDevice locationdevice ){
        mContext = ctx;
        _locationdevice = locationdevice;
    }

    public void canyouhelpme(LocationDevice locationdevice)  {
        Log.i("xcodar","canyouhelpme >>>");
       try{

           if (sendLocation("POST", locationdevice)  ){
               Log.i("xcodar","Success!");
           }else{
               Log.i("xcodar","Fail!");
           }
       }catch (Exception e){
           Log.i("xcodar"," Erro:  "+e.getMessage());
       }finally {
           Log.i("xcodar","canyouhelpme <<<<");
       }
    }

    private boolean sendLocation(String methodHttp , LocationDevice location) {
        Log.i("xcodar","LocationApi.sendLocation >>>");
        boolean success = false;
        String url = server;
        URL urlCon;
        HttpURLConnection client;
        BufferedReader reader = null;

        try {
            urlCon = new URL(url);
            client = (HttpURLConnection) urlCon.openConnection();
            client.setReadTimeout(10000);
            client.setConnectTimeout(15000);
            client.setDoInput(true);
            client.setDoOutput(true);
            client.addRequestProperty("Content-type", "application/json");
            client.setRequestMethod(methodHttp);
            Log.i("xcodar","properties!");

            client.connect();
            Log.i("xcodar","client connected!");

            OutputStream outputPost = client.getOutputStream();
            outputPost.write(writeStream(outputPost, location));
            outputPost.flush();

            if (client.getResponseCode() == HttpURLConnection.HTTP_CREATED){
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                Log.i("xcodar",sb.toString());
            }

            Log.i( "xcodar", Integer.toString(client.getResponseCode()));


            client.disconnect();
            outputPost.close();
            success = true;
        }catch (Exception e){
            Log.i("xcodar","Error: " + e.getMessage());
            return success;
        }finally {
            Log.i("xcodar","LocationApi.sendLocation <<<");
            return success;
        }

    }

    private byte[] writeStream(OutputStream outputPost, LocationDevice location) throws JSONException {
        JSONObject jl = new JSONObject();
        Date date = new Date();
        Locale local = new Locale("pt", "BR");
        Date currentTime = getInstance(local).getTime();
        jl.put("deviceId", location.get_deviceId());
        jl.put("longitude", location.get_longitude());
        jl.put("latitude", location.get_latitude());
        jl.put("dataTimeLocation", currentTime.toString());

        String json = jl.toString();
        return json.getBytes();

    }



}
