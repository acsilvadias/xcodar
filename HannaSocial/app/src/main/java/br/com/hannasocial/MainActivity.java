package br.com.hannasocial;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.FocusFinder;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {


    private CheckBox chkBoxKeepAnonimous;
    private CheckBox chkBoxBeAvailable;
    private TextView txtViewPeoplesAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);/* Aqui esta sendo referenciado o botão*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Inciar troca de mensagens agora!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnAnonimousAlert = findViewById(R.id.btn_anonimousAlert);
        //btnAnonimousAlert = (Button)findViewById(R.id.btn_anonimousAlert);
        chkBoxKeepAnonimous = (CheckBox)findViewById(R.id.chkBoxKeepAnonimous);
        chkBoxBeAvailable = (CheckBox)findViewById(R.id.chkBoxBeAvailable);
        txtViewPeoplesAvailable = (TextView)findViewById(R.id.txtViewPeoplesAvailable);

        /* Criar os respectivos eventos */
        btnAnonimousAlert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                messageToast(getApplicationContext(), "Não Implementado!", 3 );

            }
        });

        chkBoxKeepAnonimous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                if (!chkBoxKeepAnonimous.isChecked())
                {
                    messageToast(getApplicationContext(), "Atencão, ao desmarcar esta opção você deixará de ser uma pessoal anônima!", 5 );
                }

            }

        });

        chkBoxBeAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkBoxBeAvailable.isChecked())
                {
                    messageToast(getApplicationContext(), "Atencão, ativado!", 3 );
                }else
                {
                    messageToast(getApplicationContext(), "Atencão, desativado!", 3 );
                }
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

    public void messageToast( Context ctx, CharSequence message, int duration){
        Context context = ctx;
        CharSequence text = message;
        int localduration = duration;

        Toast toast = Toast.makeText(context, text, localduration);
        toast.show();
    }

}
