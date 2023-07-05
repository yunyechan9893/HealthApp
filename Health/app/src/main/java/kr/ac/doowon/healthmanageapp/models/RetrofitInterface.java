package kr.ac.doowon.healthmanageapp.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
        @POST("login")
        Call<LoginResponse> login(@Body UserRequest request);

        @POST("login/token")
        Call<JsonResponese> tokenLogin(@Body LoginTokenRequest request);

        @GET("duplicate-check/id/{id}")
        Call<JsonResponese> checkDuplicateId(@Path("id") String id);

        @GET("duplicate-check/nickname/{nickname}")
        Call<JsonResponese> checkDuplicateNickname(@Path("nickname") String nickname);
    }
