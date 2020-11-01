package com.example.eda2_martin_facundoleonel_40570462.clases.services;

import com.example.eda2_martin_facundoleonel_40570462.clases.requests.IniciarSesionRequest;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.IniciarSesionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IniciarSesionService {

    @Headers({
            "content-type: application/json"
    })

    @POST("api/login")
    Call<IniciarSesionResponse> iniciarSesion(@Body IniciarSesionRequest request);
}
