package br.com.hannasocial;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.SettingInjectorService;
import android.os.Bundle;

import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.FocusFinder;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import java.nio.file.Paths;

import static com.google.android.gms.location.LocationServices.*;

public class MainActivity extends AppCompatActivity {
    /* Verificar GPS*/
    private static final int REQUEST_CHECK_GPS = 2;
    private static final String EXTRA_DIALOG = "dialog";

    private FusedLocationProviderClient fusedLocationProviderClient;

    /* Componentes */
    private Button btnAnonimousAlert;
    private CheckBox chkBoxKeepAnonimous;
    private CheckBox chkBoxBeAvailable;
    private TextView txtViewPeoplesAvailable;

    Handler mHandler;
    boolean mShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermission();

        verifyStatusGps();

        mHandler = new Handler();
        mShowDialog = savedInstanceState == null;

        setContentView(R.layout.activity_main);
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

             checkLocatioinGPS();
            //messageToast(getApplicationContext(), "Não Implementado!", 3);

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

        fusedLocationProviderClient = getFusedLocationProviderClient(this);

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


    private void verifyStatusGps() {
        /*  Não implementado! */
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    private void checkLocatioinGPS(){
        Log.i("xcodar","checkStatusGPS >>> ");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("xcodar","return <<<< ");
            return;
        }
        Task<Location> task;
        task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                   CharSequence responseLocation = "Localidade LAT: " + location.getLatitude()+" LON: "+location.getLongitude();
                   messageToast(MainActivity.this, responseLocation,5 );
                   Log.d("AndroidClarified","Localidade" + location.getLatitude()+" "+location.getLongitude());      }
            }
        });
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

    private void sendLocation(){
        Log.i("xcodar","sendLocation >>> ");


        Log.i("xcodar","sendLocation <<< ");
    }
}
