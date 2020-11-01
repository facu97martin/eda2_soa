package com.example.eda2_martin_facundoleonel_40570462.clases.services;

import com.example.eda2_martin_facundoleonel_40570462.clases.requests.RegistrarEventoRequest;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.RegistrarEventoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistrarEventoService {
    @Headers({
            "content-type: application/json"
    })

    @POST("api/event")
    Call<RegistrarEventoResponse> registrarEvento(@Header("Authorization") String bearerToken, @Body RegistrarEventoRequest request);
}
