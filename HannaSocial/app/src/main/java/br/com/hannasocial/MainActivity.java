package br.com.hannasocial;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    /* Verificar GPS*/
    MainActivity instance;
    private static final String TAG = "xcodar";
    private static final int REQUEST_CHECK_GPS = 2;
    private static final String EXTRA_DIALOG = "dialog";
    private String mDeviceId = "";
    private String mLongitude = "";
    private String mLatitude = "";
    private String mLastLongitude = "";
    private String mLastLatitude = "";
    private String mObjId = "";
    private boolean onUpdate;
    private String mRegistrationId = "";
    private String mToken = "";

    public String getmObjId() {
        return mObjId;
    }
    public void setObjId(String mObjId) {
        this.mObjId = mObjId;
    }
    public String getLastLongitude() {
        return mLastLongitude;
    }
    public String getLastLatitude() {
        return mLastLatitude;
    }
    public void setLastLongitude(String mLastLongitude) {
        this.mLastLongitude = mLastLongitude;
    }
    public void setLastLatitude(String mLastLatitude) {
        this.mLastLatitude = mLastLatitude;
    }
    public String getRegistrationId() {return mRegistrationId;}
    public void setRegistrationId(String mRegistrationId) {this.mRegistrationId = mRegistrationId;}


    private Location mLastLocation;
    private final ThreadLocal<TelephonyManager> telephonyManager = new ThreadLocal<TelephonyManager>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private String[] mPermissions = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};


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
    private void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public MainActivity() {
        instance = this;
    }
    public String getLongitude() {
        return mLongitude;
    }
    public String getLatitude() {
        return mLatitude;
    }
    public String getmDeviceId() {
        return mDeviceId;
    }

    Handler mHandler;
    boolean mShowDialog;

    public void Timer() {
        Timer timer = new Timer();
        TaskTimer taskTime = new TaskTimer();
        timer.schedule(taskTime, 10000, 10000);
    }


    /*  Notification */
    private static final int NOTIFICATION_SIMPLE = 1;
    private static final int NOTIFICATION_COMPLETE = 2;
    private static final int NOTIFICATION_BIG = 3;
    private static final String NOTIFICATION_TITLE = "Ajude-mee!";
    private static final String NOTIFICATION_TEXT = "Por favor, estou precisando de ajuda, estou próximo(a) de você!";

    MyReceiver mReceiver;

    public static final String EXTRA_TEXTO = "texto";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestDeviceId();

        mReceiver = new MyReceiver();


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

        btnAnonimousAlert = (Button) findViewById(R.id.btn_anonimousAlert);
        chkBoxKeepAnonimous = (CheckBox) findViewById(R.id.chkBoxKeepAnonimous);
        chkBoxBeAvailable = (CheckBox) findViewById(R.id.chkBoxBeAvailable);
        //txtViewPeoplesAvailable = (TextView) findViewById(R.id.txtViewPeoplesAvailable);

        String string = getIntent().getStringExtra(EXTRA_TEXTO);
        TextView txt = findViewById(R.id.txtDetail);
        txt.setText(string);

        TextView txtToken = findViewById(R.id.txtToken);
       // mToken = FirebaseInstanceId.getInstance().getToken(" ",null);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mToken = instanceIdResult.getToken();
                Log.i(TAG,"Token: " + mToken);
            }
        });

        Log.i(TAG,"Token: " + mToken);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(75000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        /* Criar os respectivos eventos */
        btnAnonimousAlert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                GetHelp();
                messageToast(getApplicationContext(), "Solicitação de ajuda enviada!", 5);

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        setmLatitude(Double.toString(mLastLocation.getLatitude()));
                        setmLongitude(Double.toString(mLastLocation.getLongitude()));
                    }
                }
            });
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        setmLatitude(Double.toString(location.getLatitude()));
                        setmLongitude(Double.toString(location.getLongitude()));

                        if (!getLastLatitude().equals(getLatitude()) && getLastLongitude().equals(getLongitude())) {
                            Log.i("xcodar", "Nâo Atualizar!");
                            CharSequence responseLocation = "ObjId: " + getmObjId() + " Imei: " + getmDeviceId() + " Localidade LAT: " + getLatitude() + " LON: " + getLongitude();
                            Log.i("xcodar", responseLocation.toString());
                            return;
                        }
                        SendLocation();
                        setLastLatitude(getLatitude());
                        setLastLongitude(getLongitude());
                        CharSequence responseLocation = "ObjId: " + getmObjId() + " Imei: " + getmDeviceId() + " Localidade LAT: " + getLatitude() + " LON: " + getLongitude();
                        messageToast(MainActivity.this, responseLocation, 5);
                        Log.i("xcodar", responseLocation.toString());

                    } else {
                        messageToast(MainActivity.this, "Falha ao acessar a localização!", 7);
                    }
                }

            }
        };
        //Timer();
        createNotificationChannels();
    }

    public void createNotificationSimple(View view) {
         NotificationUtils.createNotificationSimple(
                MainActivity.this,
                NOTIFICATION_TEXT,
                 NOTIFICATION_TITLE
        );
        Log.i("xcodar", "createNotificationSimple !!! ");
    }

    class TaskTimer extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("xcodar", "TimerTask()");
                    getLastLocatioinGPS();
                }
            });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("xcodar", "onStart()");
        requestPermission();
        setmDeviceId(getPhonyId());
        onUpdate=true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }



    private void requestDeviceId() {
        telephonyManager.set((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE));
         Log.i("xcodar", "requestDeviceId()");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                            Log.i("xcodar", "onRequestPermissionsResult()" + permissions[i] + " NOT PERMISSION_GRANTED");
                        } else {
                            Log.i("xcodar", "onRequestPermissionsResult()" + permissions[i] + " PERMISSION_GRANTED");
                        }
                    } else {
                        Log.i("xcodar", "onRequestPermissionsResult()" + permissions[i] + " NOT PERMISSION_GRANTED");
                    }
                }

            /* if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_DIALOG, mShowDialog);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mShowDialog = savedInstanceState.getBoolean(EXTRA_DIALOG, true);
    }

    private String getPhonyId() {
        Log.i("xcodar", "getPhonyId >>");
        Log.i("xcodar", "SDK:" + android.os.Build.VERSION.SDK_INT);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            if (android.os.Build.VERSION.SDK_INT < 26) return telephonyManager.get().getDeviceId();
            else return telephonyManager.get().getImei();
        Log.i("xcodar", "getPhonyId <<");
        return "+5581995922332";
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                mPermissions,
                101);
    }

    private void getLastLocatioinGPS() {
        Log.i("xcodar", "getLastLocatioinGPS >>> ");
        if (!gpsEnable()) {
            messageToast(MainActivity.this, "Favor ativar o serviço de localização(GPS)!", 7);
            fusedLocationProviderClient.flushLocations();
            return;
        }
        Log.i("xcodar", "GPS Ativado! ");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                                setmLatitude(Double.toString(mLastLocation.getLatitude()));
                                setmLongitude(Double.toString(mLastLocation.getLongitude()));

                                if (!getLastLatitude().equals(getLatitude()) && getLastLongitude().equals(getLongitude())) {
                                    Log.i("xcodar", "Nâo Atualizar!");
                                    CharSequence responseLocation = "ObjId: " + getmObjId() + " Imei: " + getmDeviceId() + " Localidade LAT: " + getLatitude() + " LON: " + getLongitude();
                                    Log.i("xcodar", responseLocation.toString());
                                    return;
                                }
                                SendLocation();
                                setLastLatitude(getLatitude());
                                setLastLongitude(getLongitude());
                                CharSequence responseLocation = "ObjId: " + getmObjId() + " Imei: " + getmDeviceId() + " Localidade LAT: " + getLatitude() + " LON: " + getLongitude();

                                messageToast(MainActivity.this, responseLocation, 5);
                                Log.i("xcodar", responseLocation.toString());

                            } else {
                                messageToast(MainActivity.this, "Falha ao acessar a localização!" + task.getException(), 7);
                            }
                        }
                    });
        } else {
            requestPermission();
        }
        Log.i("xcodar", "getLastLocatioinGPS <<< ");
    }

    private boolean gpsEnable() {
        try {
            Log.i("xcodar", "gpsEnable >>> ");
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean result = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            return result;

        } catch (Exception ex) {
            Log.i("xcodar", "gpsEnable Erro:" + ex.getMessage());
            return false;
        } finally {
            Log.i("xcodar", "gpsEnable <<< ");
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
        Log.i("xcodar", "SendLocation >>> ");
        try {
            LocationDevice locationdevice = new LocationDevice(getmDeviceId(), getLatitude(), getLongitude(), getmObjId());

            new LocationWebApi(this, locationdevice).execute();

        } catch (Exception e) {
            Log.i("xcodar", "Error SendLocation: " + e.getMessage());
        } finally {
            Log.i("xcodar", "SendLocation <<< ");
        }
    }

    protected void GetHelp() {
        Log.i("xcodar", "GetHelp >>> ");
        try {
            LocationDevice locationdevice = new LocationDevice(getmDeviceId(), getLatitude(), getLongitude(), getmObjId());
            new HelpWebApi(this, locationdevice).execute();
        } catch (Exception e) {
            Log.i("xcodar", "Error SendLocation: " + e.getMessage());
        } finally {
            Log.i("xcodar", "GetHelp <<< ");
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }else
        {
            requestPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (onUpdate) startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!onUpdate) stopLocationUpdates();
    }

    private void stopLocationUpdates() {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

     class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            messageToast(MainActivity.this,intent.getAction(), Toast.LENGTH_LONG);
            Log.i("xcodar", "MyReceiver !!! ");
        }
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    NotificationUtils.ACTION_NOTIFICATION,
                    NotificationUtils.CHANNEL_NAME ,
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(NotificationUtils.CHANNEL_DESCRIPTION);
            NotificationManager managet = getSystemService(NotificationManager.class);
            managet.createNotificationChannel(channel1);
        }else{
            registerReceiver(mReceiver,
                    new IntentFilter(NotificationUtils.ACTION_DELETE));
            registerReceiver(mReceiver,
                    new IntentFilter(NotificationUtils.ACTION_NOTIFICATION));

        }
    }

}
