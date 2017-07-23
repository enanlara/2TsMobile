package br.com.a2ts_mobile.Things_Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Enan on 6/17/2017.
 */

public interface ThingsService {

    @GET("searchByLocation/{location}")
    public Call<List<ThingsModel>> getThingByLocation(@Path("location") String location);

    @GET("allThings")
    public Call<List<ThingsModel>> getAllThings();

    @GET("allLocations")
    public Call<List<Location>> getAllLocations();
}
