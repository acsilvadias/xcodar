package br.com.hannasocial;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_TEXTO = "texto";

    public static final String CHANNEL_1_ID = "channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("xcodar", "DetailActivity onCreate >>>");
        String string = getIntent().getStringExtra(EXTRA_TEXTO);

        TextView txt = findViewById(R.id.txtDetail);
        txt.setText(string);
    }
}
