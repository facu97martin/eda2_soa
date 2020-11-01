package com.example.eda2_martin_facundoleonel_40570462.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eda2_martin_facundoleonel_40570462.R;

public class InicioActivity extends Activity {

    private TextView textoBateria;

    private Button botonIniciarSesion;
    private Button botonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        botonRegistrarse = (Button) findViewById(R.id.boton_registrarse);
        botonIniciarSesion = (Button) findViewById(R.id.boton_iniciar_sesion);

        textoBateria = (TextView) findViewById(R.id.texto_bateria);
        textoBateria.setText("Bateria: " + String.valueOf(obtenerBateria()) + "%");

        botonRegistrarse.setOnClickListener(botonesListener);
        botonIniciarSesion.setOnClickListener(botonesListener);
    }

    private int obtenerBateria() {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, iFilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return (int) (level * 100 / (float) scale);
    }

    private View.OnClickListener botonesListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent;

            switch (v.getId()) {
                case R.id.boton_iniciar_sesion:
                    intent = new Intent(InicioActivity.this, IniciarSesionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.boton_registrarse:
                    intent = new Intent(InicioActivity.this, RegistrarseActivity.class);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en el listener de botones: " + v.getId(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}