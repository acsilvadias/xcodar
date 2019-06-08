package br.com.hannasocial;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import android.util.Log;

public class LocationWebApi {
    public static final String server = "http://192.168.11.3:3003/api";
    private static final String mWebApi = server ;
    private Context mContext;

    public LocationWebApi(Context ctx, LocationDevice locationdevice ){
        mContext = ctx;

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
        boolean doOutPut = !"DELETE".equals(methodHttp);
        String url = server;
        URL urlCon;
        HttpURLConnection client;


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
            outputPost.close();

            client.disconnect();
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
        jl.put("deviceId", location.get_deviceId());
        jl.put("longitude", location.get_longitude());
        jl.put("latitude", location.get_latitude());
        jl.put("latitude",date);

        String json = jl.toString();
        return json.getBytes();

    }
}
