package br.com.hannasocial;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.getInstance;

public class HelpWebApi extends AsyncTask<LocationDevice, Void, Void> {
    /*
     * DNS IPV4: ec2-18-222-248-86.us-east-2.compute.amazonaws.com
     * LOCAL: "http://192.168.11.3:3003/api/locationDevices"
     * */
    private String TAG = "xcodar";
    private String methodHttp = "POST";

    public static final String server = "http://18.222.248.86:3003/api/helpDevices";
    private static final String mWebApi = server ;
    private MainActivity mContext;
    private LocationDevice _locationdevice;
    @Override
    protected Void doInBackground(LocationDevice... locationDevices) {
        canyouhelpme(_locationdevice);
        return null;
    }

    public HelpWebApi(MainActivity ctx, LocationDevice locationdevice ){
        mContext = ctx;
        _locationdevice = locationdevice;
    }

    public void canyouhelpme(LocationDevice locationdevice)  {
        Log.i(TAG,"canyouhelpme >>>");
        try{
            //if (locationdevice.get_objId() != null){methodHttp = "PUT"; }
            if (sendLocation(methodHttp, locationdevice)  ){
                Log.i(TAG,"Success!");
            }else{
                Log.i(TAG,"Fail!");
            }
        }catch (Exception e){
            Log.i(TAG," Erro:  "+e.getMessage());
        }finally {
            Log.i(TAG,"canyouhelpme <<<<");
        }
    }

    private boolean sendLocation(String methodHttp , LocationDevice location) {
        Log.i("xcodar","HelpWebApi.sendLocation >>>");
        boolean success = false;
        String url = mWebApi;
        URL urlCon;
        HttpURLConnection client;
        BufferedReader reader = null;

        try {
            if(methodHttp.equals("PUT")){url += "/:id"; }
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

            Log.i("xcodar","body: " + outputPost.toString());
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
                JSONObject jo = new JSONObject(sb.toString());
                mContext.setObjId(jo.getString("_id"));
            }
            Log.i( "xcodar", Integer.toString(client.getResponseCode()));

            client.disconnect();
            outputPost.close();
            success = true;
        }catch (Exception e){
            Log.i("xcodar","Error: " + e.getMessage());
            return success;
        }finally {
            Log.i("xcodar","HelpWebApi.sendLocation <<<");
            return success;
        }

    }

    private byte[] writeStream(OutputStream outputPost, LocationDevice location) throws JSONException {
        JSONObject jo = new JSONObject();
        Date date = new Date();

        Locale local = new Locale("pt", "BR");
        Date currentTime = getInstance(local).getTime();
        jo.put("locationDeviceId", location.get_deviceId());
        jo.put("location.type", "Point");
        jo.accumulate( "location.coordinates", location.get_longitude() );
        jo.accumulate( "location.coordinates", location.get_latitude() );
        jo.accumulate("snConfirma","n");
        jo.put("token",location.get_token());
        String json = jo.toString();
        return json.getBytes();
    }



}
