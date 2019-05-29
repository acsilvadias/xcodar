package br.com.hannasocial;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.MediaMetadataCompatApi21;
=======
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
>>>>>>> Hanna28052019
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

<<<<<<< HEAD
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
=======
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
>>>>>>> Hanna28052019
import com.google.android.gms.tasks.Task;

import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {
    /* Verificar GPS*/
    private static final int REQUEST_CHECK_GPS = 2;
    private static final String EXTRA_DIALOG = "dialog";

    Handler mHandler;
    boolean mShowDialog;
    int mAttempts;

    /* Componentes */
    private CheckBox chkBoxKeepAnonimous;
    private CheckBox chkBoxBeAvailable;
    private TextView txtViewPeoplesAvailable;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        Button btnAnonimousAlert = findViewById(R.id.btn_anonimousAlert);
        //btnAnonimousAlert = (Button)findViewById(R.id.btn_anonimousAlert);
        chkBoxKeepAnonimous = (CheckBox) findViewById(R.id.chkBoxKeepAnonimous);
        chkBoxBeAvailable = (CheckBox) findViewById(R.id.chkBoxBeAvailable);
        txtViewPeoplesAvailable = (TextView) findViewById(R.id.txtViewPeoplesAvailable);

        /* Criar os respectivos eventos */
        btnAnonimousAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messageToast(getApplicationContext(), "Não Implementado!", 3);

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.i("LOG", "latitude: " + location.getLatitude());
                            Log.i("LOG", "longitude: " + location.getLongitude());
                        }
                    }
                });

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_DIALOG,mShowDialog);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mShowDialog = savedInstanceState.getBoolean(EXTRA_DIALOG, true);
    }

    private void checkStatusGPS(){
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder locationSettingsRequest =
                new LocationSettingsRequest.Builder();
        locationSettingsRequest.setAlwaysShow(true);
        locationSettingsRequest.addLocationRequest(locationRequest);

        /*Task<LocationSettingsResult> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest.build());*/



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


}
