package com.example.eda2_martin_facundoleonel_40570462.activitys;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eda2_martin_facundoleonel_40570462.R;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.RegistrarEventoResponse;
import com.example.eda2_martin_facundoleonel_40570462.clases.services.RegistrarEventoService;

import java.text.DecimalFormat;

import static com.example.eda2_martin_facundoleonel_40570462.clases.utils.Utils.registrarEvento;

public class SensoresActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sAcelerometro;
    private Sensor sGiroscopo;
    private Sensor sProximidad;
    private Sensor sLuz;

    private EditText texto_acelerometro_x;
    private EditText texto_acelerometro_y;
    private EditText texto_acelerometro_z;
    private EditText texto_giroscopio_x;
    private EditText texto_giroscopio_y;
    private EditText texto_giroscopio_z;
    private EditText texto_proximidad_valor;
    private EditText texto_luz_valor;

    private String token = "token";
    private String tokenRefresh = "TokenRefresh";

    DecimalFormat decFormat = new DecimalFormat("###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras().getBundle("bundle");
        token = bundle.getString("token");
        tokenRefresh = bundle.getString("token_refresh");

        texto_acelerometro_x = (EditText) findViewById(R.id.text_acelerometro_x);
        texto_acelerometro_y = (EditText) findViewById(R.id.text_acelerometro_y);
        texto_acelerometro_z = (EditText) findViewById(R.id.text_acelerometro_z);

        texto_giroscopio_x = (EditText) findViewById(R.id.text_giroscopio_x);
        texto_giroscopio_y = (EditText) findViewById(R.id.text_giroscopio_y);
        texto_giroscopio_z = (EditText) findViewById(R.id.text_giroscopio_z);

        texto_proximidad_valor = (EditText) findViewById(R.id.text_proximidad_valor);

        texto_luz_valor = (EditText) findViewById(R.id.text_luz_valor);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        initSensores();
    }

    protected void initSensores() {
        sAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sGiroscopo = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        activarSensores();
    }

    protected void activarSensores() {
        sensorManager.registerListener(this, sAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sGiroscopo, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sProximidad, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sLuz, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void pausarSensores() {
        sensorManager.unregisterListener(this, sAcelerometro);
        sensorManager.unregisterListener(this, sGiroscopo);
        sensorManager.unregisterListener(this, sProximidad);
        sensorManager.unregisterListener(this, sLuz);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activarSensores();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausarSensores();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        /*
        Solo se registran los sensores de proximidad y luz ya que los de acelerometro
        y giroscopio cambian con demasiada frecuencia y no es la idea sobrecargar la
        base de datos ni la cantidad de requests de la aplicacion
         */

        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    texto_acelerometro_x.setText("x: " + decFormat.format(event.values[0]));
                    texto_acelerometro_y.setText("y: " + decFormat.format(event.values[1]));
                    texto_acelerometro_z.setText("z: " + decFormat.format(event.values[2]));
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    texto_giroscopio_x.setText("x: " + decFormat.format(event.values[0]));
                    texto_giroscopio_y.setText("y: " + decFormat.format(event.values[1]));
                    texto_giroscopio_z.setText("z: " + decFormat.format(event.values[2]));
                    break;
                case Sensor.TYPE_PROXIMITY:
                    String valor_proximidad_text = decFormat.format(event.values[0]);
                    texto_proximidad_valor.setText("d: " + valor_proximidad_text);
                    registrarEvento(getApplicationContext(), token, "Sensor_Proximidad", "Sensor en valor " + valor_proximidad_text);
                    break;
                case Sensor.TYPE_LIGHT:
                    String valor_luz_text = decFormat.format(event.values[0]);
                    texto_luz_valor.setText("lux: " + valor_luz_text);
                    registrarEvento(getApplicationContext(), token, "Sensor_Luz", "Sensor en valor " + valor_luz_text);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en el listener de sensores: " + event.sensor.getName(), Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}


