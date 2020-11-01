package com.example.eda2_martin_facundoleonel_40570462.clases.services;

import com.example.eda2_martin_facundoleonel_40570462.clases.requests.RegistrarseRequest;
import com.example.eda2_martin_facundoleonel_40570462.clases.responses.RegistrarseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistrarseService {

    @Headers({
            "content-type: application/json"
    })

    @POST("api/register")
    Call<RegistrarseResponse> registrarse(@Body RegistrarseRequest request);
}
