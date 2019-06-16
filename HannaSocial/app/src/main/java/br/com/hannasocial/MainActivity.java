package br.com.hannasocial;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    /* Verificar GPS*/
    private static final int REQUEST_CHECK_GPS = 2;
    private static final String EXTRA_DIALOG = "dialog";
    private String mDeviceId = "";
    private String mLongitude = "";
    private String mLatitude = "";
    private String mLastLongitude = "";
    private String mLastLatitude = "";

    public String getmLastLongitude() {
        return mLastLongitude;
    }

    public void setmLastLongitude(String mLastLongitude) {
        this.mLastLongitude = mLastLongitude;
    }

    public String getmLastLatitude() {
        return mLastLatitude;
    }

    public void setmLastLatitude(String mLastLatitude) {
        this.mLastLatitude = mLastLatitude;
    }


    private final ThreadLocal<TelephonyManager> telephonyManager = new ThreadLocal<TelephonyManager>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String[] mPermissions ={Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.ACCESS_FINE_LOCATION ,
                                            Manifest.permission.ACCESS_COARSE_LOCATION};
    private Location mLastLocation;

    /* Componentes */
    private Button btnAnonimousAlert;
    private CheckBox chkBoxKeepAnonimous;
    private CheckBox chkBoxBeAvailable;
    private TextView txtViewPeoplesAvailable;

    private void setmDeviceId(String mDeviceId) {
        this.mDeviceId = mDeviceId;
    }
    private void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }
    private void setmLatitude(String mLatitude) { this.mLatitude = mLatitude; }

    public MainActivity() {   }
    public String getLongitude() {
        return mLongitude;
    }
    public String getLatitude() {
        return mLatitude;
    }
    public String getmDeviceId() { return mDeviceId; }

    Handler mHandler;
    boolean mShowDialog;

    public void Timer(){
        Timer timer = new Timer();
        TaskTimer taskTime = new TaskTimer();
        timer.schedule(taskTime, 10000, 10000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestDeviceId();

        mHandler = new Handler();
        mShowDialog = savedInstanceState == null;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);/* Aqui esta sendo referenciado o botão*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Iniciar troca de mensagens agora!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnAnonimousAlert = (Button)findViewById(R.id.btn_anonimousAlert);
        chkBoxKeepAnonimous = (CheckBox) findViewById(R.id.chkBoxKeepAnonimous);
        chkBoxBeAvailable = (CheckBox) findViewById(R.id.chkBoxBeAvailable);
        txtViewPeoplesAvailable = (TextView) findViewById(R.id.txtViewPeoplesAvailable);

        /* Criar os respectivos eventos */
        btnAnonimousAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocatioinGPS();
         }
        });

        chkBoxKeepAnonimous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chkBoxKeepAnonimous.isChecked()) {
                    messageToast(getApplicationContext(), "Atencão, ao desmarcar esta opção você deixará de ser uma pessoal anônima!", 5);
                }
            }

        });

        chkBoxBeAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkBoxBeAvailable.isChecked()) {
                    messageToast(getApplicationContext(), "Atencão, ativado!", 3);
                } else {
                    messageToast(getApplicationContext(), "Atencão, desativado!", 3);
                }
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Timer();
    }

    class TaskTimer extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("xcodar","TimerTask()");
                    getLastLocatioinGPS();
                }
            });
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.i("xcodar","onStart()");
        requestPermission();
        setmDeviceId(getPhonyId());
    }

    private void requestDeviceId() {
       telephonyManager.set((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE));
         /*
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            Log.i("xcodar","return requestDeviceId()");
            return;
        }*/
        Log.i("xcodar","requestDeviceId()");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:

                for (int i=0;i<grantResults.length;i++   ){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    {
                        if (ActivityCompat.checkSelfPermission(this, permissions[i] ) != PackageManager.PERMISSION_GRANTED){
                            Log.i("xcodar", "onRequestPermissionsResult()" + permissions[i] + " NOT PERMISSION_GRANTED");
                        }else {
                            Log.i("xcodar", "onRequestPermissionsResult()" + permissions[i] + " PERMISSION_GRANTED");
                        }
                    }else{
                        Log.i("xcodar","onRequestPermissionsResult()" + permissions[i] + " NOT PERMISSION_GRANTED" );
                    }
                }

            /*    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        Log.i("xcodar"," return onRequestPermissionsResult()");
                        return;
                    }
                    Log.i("xcodar","onRequestPermissionsResult()");
                    getPhonyId();
                } else {
                    Toast.makeText(MainActivity.this,"Verificar Permissão!",Toast.LENGTH_LONG).show();
                }*/

                break;
            default:
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_DIALOG,mShowDialog);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mShowDialog = savedInstanceState.getBoolean(EXTRA_DIALOG, true);
    }

    private String getPhonyId() {
        Log.i("xcodar","getPhonyId >>");
        Log.i("xcodar","SDK:"+ android.os.Build.VERSION.SDK_INT);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            if (android.os.Build.VERSION.SDK_INT < 26) return telephonyManager.get().getDeviceId();
            else return telephonyManager.get().getImei();
        Log.i("xcodar","getPhonyId <<");
        return "+5581995922332";
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                mPermissions ,
                101);
    }

    private void getLastLocatioinGPS(){
        Log.i("xcodar","getLastLocatioinGPS >>> ");
        if (!gpsEnable()) {
            messageToast(MainActivity.this, "Favor ativar o serviço de localização(GPS)!",7 );
            fusedLocationProviderClient.flushLocations();
            return;
        }
        Log.i("xcodar","GPS Ativado! ");

        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task){
                if ( task.isSuccessful() && task.getResult() != null)
                {
                    mLastLocation = task.getResult();
                    setmLatitude(Double.toString(mLastLocation.getLatitude()));
                    setmLongitude(Double.toString(mLastLocation.getLongitude()));

                    if (getmLastLatitude().equals(getLatitude()) && getmLastLongitude().equals(getLongitude()))
                    {   Log.i("xcodar","Localidade" + getLongitude() +" "+ getLatitude());
                        return;
                    }
                    SendLocation();
                    setmLastLatitude(getLatitude());
                    setmLastLongitude(getLongitude());
                    CharSequence responseLocation = "Imei: " + getmDeviceId()  + " Localidade LAT: " + getLatitude()+" LON: "+getLongitude();
                    messageToast(MainActivity.this, responseLocation,5 );
                    Log.i("xcodar","Localidade" + getLongitude() +" "+ getLatitude());

            }else{
                    messageToast(MainActivity.this, "Falha ao acessar a localização!"+ task.getException(),7 );
            }
                }
        });
        Log.i("xcodar","getLastLocatioinGPS <<< ");
    }

    private boolean gpsEnable() {
        try {
            Log.i("xcodar","gpsEnable >>> ");
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean result = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            return result;

        }catch (Exception ex){
            Log.i("xcodar","gpsEnable Erro:" + ex.getMessage());
            return false;
        }finally {
            Log.i("xcodar","gpsEnable <<< ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void messageToast(Context ctx, CharSequence message, int duration) {
        Context context = ctx;
        CharSequence text = message;
        int localduration = duration;

        Toast toast = Toast.makeText(context, text, localduration);
        toast.show();
    }

    /* Enviar e consumir api rest */

    protected void SendLocation() {

        Log.i("xcodar","SendLocation >>> ");

        try{
            LocationDevice locationdevice = new LocationDevice(getmDeviceId(),getLatitude(),getLongitude());

            new LocationWebApi(this,locationdevice).execute();

        }catch (Exception e){
            Log.i("xcodar","Error SendLocation: " + e.getMessage());
        }finally {
            Log.i("xcodar","SendLocation <<< ");
        }
    }

}
