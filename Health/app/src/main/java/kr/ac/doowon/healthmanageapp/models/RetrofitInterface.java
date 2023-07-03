package kr.ac.doowon.healthmanageapp.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
        @POST("login")
        Call<LoginResponse> login(@Body LoginRequest request);

        @POST("login/token")
        Call<LoginTokenResponse> tokenLogin(@Body LoginTokenRequest request);
    }
