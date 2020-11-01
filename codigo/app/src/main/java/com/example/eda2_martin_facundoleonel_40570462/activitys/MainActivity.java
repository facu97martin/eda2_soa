package com.example.eda2_martin_facundoleonel_40570462.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eda2_martin_facundoleonel_40570462.R;

public class MainActivity extends Activity {

    private Button botonSensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonSensores = (Button) findViewById(R.id.boton_sensores);

        botonSensores.setOnClickListener(botonesListener);
    }

    private final View.OnClickListener botonesListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras().getBundle("bundle");

            switch (v.getId()) {
                case R.id.boton_sensores:
                    intent = new Intent(MainActivity.this, SensoresActivity.class);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en el listener de botones: " + v.getId(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
