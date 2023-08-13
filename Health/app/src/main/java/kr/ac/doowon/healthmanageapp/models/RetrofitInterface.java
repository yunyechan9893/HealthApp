package kr.ac.doowon.healthmanageapp.models;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
        @POST("login")
        Single<LoginResponse> login(@Body LoginRequest request);

        @POST("login/token")
        Call<JsonResponese> tokenLogin(@Body LoginTokenRequest request);

        @GET("login/diet/{id}")
        Single<DietResponese> getDiet( @Path("id") String id );

        @GET("login/target-kcal/{id}")
        Single<TargetKcalResponese> getTargetKcal( @Path("id") String id );
        @POST("signup")
        Call<JsonResponese> signup(@Body UserRequest request);

        @GET("duplicate-check/id/{id}")
        Call<JsonResponese> checkDuplicateId(@Path("id") String id);

        @GET("duplicate-check/nickname/{nickname}")
        Call<JsonResponese> checkDuplicateNickname(@Path("nickname") String nickname);
    }
