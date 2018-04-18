package br.com.a2ts_mobile.Things_Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Enan on 6/17/2017.
 */

public interface ThingsService {

    @GET("search_things_by_location/token={token}&locaid={location}")
    public Call<List<ThingsModel>> getThingByLocation(@Path("token") String token, @Path("location") String location);

    @GET("search_things_by_num/token={token}&num={num}")
    public Call<ThingsModel> getThingByNum(@Path("token") String token, @Path("num") String num);

    @GET("search_things_missing_by_location/token={token}&locaid={location}")
    public Call<List<ThingsModel>> getThingsMissingByLocation(@Path("token") String token, @Path("location") String location);

    @GET("search_things_over_by_location/token={token}&locaid={location}")
    public Call<List<ThingsModel>> getThingsOverByLocation(@Path("token") String token, @Path("location") String location);

    @GET("allThings")
    public Call<List<ThingsModel>> getAllThings();

    @GET("search_locations/token={token}")
    public Call<List<LocationModel>> getAllLocations(@Path("token") String token);
}
