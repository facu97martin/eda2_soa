package com.example.eda2_martin_facundoleonel_40570462.clases.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.eda2_martin_facundoleonel_40570462.R;
import com.example.eda2_martin_facundoleonel_40570462.clases.requests.RegistrarEventoRequest;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.RegistrarEventoResponse;
import com.example.eda2_martin_facundoleonel_40570462.clases.services.RegistrarEventoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {


    public static void registrarEvento(Context applicationContext, String bearerToken, String typeEvents, String description) {

        RegistrarEventoRequest request = new RegistrarEventoRequest();

        request.setEnv(applicationContext.getString(R.string.environment));
        request.setType_events(typeEvents);
        request.setDescription(description);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(applicationContext.getString(R.string.soa_server))
                .build();

        RegistrarEventoService registrarEventoService = retrofit.create(RegistrarEventoService.class);
        Call<RegistrarEventoResponse> call = registrarEventoService.registrarEvento("Bearer " + bearerToken, request);

        call.enqueue(new Callback<RegistrarEventoResponse>() {
            @Override
            public void onResponse(Call<RegistrarEventoResponse> call, Response<RegistrarEventoResponse> response) {

                if (response.isSuccessful()) {
                    Log.i("Response Success", response.body().toString());

                } else {
                    Log.i("Response no Success", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RegistrarEventoResponse> call, Throwable t) {
                Log.i("Boton", t.getMessage());
            }
        });

    }

    public static boolean conectadoAInternet(Context applicationContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

}
