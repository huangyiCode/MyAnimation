package com.example.huangyi.myanimation.model;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by huangyi on 16/5/13.
 */
public interface ApiService {
    @GET("service/getIpInfo.php")
    Call<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);

}
