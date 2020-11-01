package com.example.eda2_martin_facundoleonel_40570462.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eda2_martin_facundoleonel_40570462.R;
import com.example.eda2_martin_facundoleonel_40570462.clases.requests.RegistrarseRequest;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.RegistrarseResponse;
import com.example.eda2_martin_facundoleonel_40570462.clases.services.RegistrarseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.eda2_martin_facundoleonel_40570462.clases.utils.Utils.conectadoAInternet;
import static com.example.eda2_martin_facundoleonel_40570462.clases.utils.Utils.registrarEvento;

public class RegistrarseActivity extends Activity {

    private Button botonFinalizarRegistro;

    private EditText textAmbiente;
    private EditText textNombre;
    private EditText textApellido;
    private EditText textDNI;
    private EditText textMail;
    private EditText textPassword;
    private EditText textCommission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        botonFinalizarRegistro = (Button) findViewById(R.id.boton_finalizar_registro);
        botonFinalizarRegistro.setOnClickListener(botonesListener);

        textAmbiente = (EditText) findViewById(R.id.texto_ambiente_registrarse);
        textNombre = (EditText) findViewById(R.id.texto_nombre_registrarse);
        textApellido = (EditText) findViewById(R.id.texto_apellido_registrarse);
        textDNI = (EditText) findViewById(R.id.texto_dni_registrarse);
        textMail = (EditText) findViewById(R.id.texto_mail_registrarse);
        textPassword = (EditText) findViewById(R.id.texto_contraseña_registrarse);
        textCommission = (EditText) findViewById(R.id.texto_comision_registrarse);

    }

    private View.OnClickListener botonesListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.boton_finalizar_registro:

                    if (!conectadoAInternet(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Sin conexión a internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    RegistrarseRequest request = new RegistrarseRequest();

                    request.setEnv(textAmbiente.getText().toString());
                    request.setName(textNombre.getText().toString());
                    request.setLastname(textApellido.getText().toString());
                    request.setDni(Long.parseLong(textDNI.getText().toString()));
                    request.setEmail(textMail.getText().toString());
                    request.setPassword(textPassword.getText().toString());
                    request.setCommission(Long.parseLong(textCommission.getText().toString()));

                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(getString(R.string.soa_server))
                            .build();

                    RegistrarseService registrarseService = retrofit.create(RegistrarseService.class);

                    Call<RegistrarseResponse> call = registrarseService.registrarse(request);
                    Toast.makeText(getApplicationContext(), "Esperando respuesta del servidor...", Toast.LENGTH_SHORT).show();

                    call.enqueue(new Callback<RegistrarseResponse>() {
                        @Override
                        public void onResponse(Call<RegistrarseResponse> call, Response<RegistrarseResponse> response) {

                            if (response.isSuccessful()) {
                                Log.i("Response Success", response.body().toString());
                                Intent intent = new Intent(RegistrarseActivity.this, MainActivity.class);

                                String token = response.body().getToken();
                                Bundle bundle = new Bundle();
                                bundle.putString("token", token);
                                bundle.putString("token_refresh", response.body().getToken_refresh());

                                intent.putExtra("bundle", bundle);

                                registrarEvento(getApplicationContext(), token, "Registro", "Registro del mail " + textMail.getText().toString());

                                startActivity(intent);
                            } else {
                                Log.i("Response no Success", response.errorBody().toString());
                                Toast.makeText(getApplicationContext(), "Error al registrarse - onResponse", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegistrarseResponse> call, Throwable t) {
                            Log.i("Boton", t.getMessage());
                            Toast.makeText(getApplicationContext(), "Fallo al registrarse - onFailure", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en el listener de botones: " + v.getId(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
