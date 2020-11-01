package com.example.eda2_martin_facundoleonel_40570462.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eda2_martin_facundoleonel_40570462.R;
import com.example.eda2_martin_facundoleonel_40570462.clases.requests.IniciarSesionRequest;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.IniciarSesionResponse;
import com.example.eda2_martin_facundoleonel_40570462.clases.services.IniciarSesionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.eda2_martin_facundoleonel_40570462.clases.utils.Utils.conectadoAInternet;
import static com.example.eda2_martin_facundoleonel_40570462.clases.utils.Utils.registrarEvento;

public class IniciarSesionActivity extends Activity {

    private Button botonFinalizarInicioSesion;

    private EditText textMail;
    private EditText textPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        botonFinalizarInicioSesion = (Button) findViewById(R.id.boton_finalizar_inicio_sesion);
        botonFinalizarInicioSesion.setOnClickListener(botonesListener);

        textMail = (EditText) findViewById(R.id.texto_mail_login);
        textPassword = (EditText) findViewById(R.id.texto_contraseña_login);
    }

    private View.OnClickListener botonesListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.boton_finalizar_inicio_sesion:

                    if(!conectadoAInternet(getApplicationContext())){
                        Toast.makeText(getApplicationContext(), "Sin conexión a internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    IniciarSesionRequest request = new IniciarSesionRequest();

                    request.setEmail(textMail.getText().toString());
                    request.setPassword(textPassword.getText().toString());

                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(getString(R.string.soa_server))
                            .build();

                    IniciarSesionService iniciarSesionService = retrofit.create(IniciarSesionService.class);
                    Call<IniciarSesionResponse> call = iniciarSesionService.iniciarSesion(request);
                    Toast.makeText(getApplicationContext(), "Esperando respuesta del servidor...", Toast.LENGTH_SHORT).show();

                    call.enqueue(new Callback<IniciarSesionResponse>() {
                        @Override
                        public void onResponse(Call<IniciarSesionResponse> call, Response<IniciarSesionResponse> response) {

                            if (response.isSuccessful()) {


                                Log.i("Response Success", response.body().toString());
                                Intent intent = new Intent(IniciarSesionActivity.this, MainActivity.class);

                                String token = response.body().getToken();
                                String tokenRefresh = response.body().getToken_refresh();
                                Bundle bundle = new Bundle();
                                bundle.putString("token", token);
                                bundle.putString("token_refresh", tokenRefresh);

                                intent.putExtra("bundle", bundle);

                                registrarEvento(getApplicationContext(), token, "Inicio_Sesion", "Inicio de sesion del mail " + textMail.getText().toString());

                                startActivity(intent);
                            } else {
                                Log.i("Response no Success", response.errorBody().toString());
                                Toast.makeText(getApplicationContext(), "Error en el inicio de sesion - onResponse", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<IniciarSesionResponse> call, Throwable t) {
                            Log.i("Boton", t.getMessage());
                            Toast.makeText(getApplicationContext(), "Fallo en el inicio de sesion - onFailure", Toast.LENGTH_SHORT).show();
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