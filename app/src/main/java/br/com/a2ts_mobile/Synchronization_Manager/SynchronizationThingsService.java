package br.com.a2ts_mobile.Synchronization_Manager;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Enan on 6/17/2017.
 */

public interface SynchronizationThingsService {

    @GET("edit_thing/token={token}&num={num}&locaid_c={locationCurrent}&situation={situation}&state={state}&note={note}&locaid={location}")
    public Call<String> synchronizeThings(@Path("token") String token, @Path("num") String nrThings, @Path("locationCurrent") String locationCurrent, @Path("situation") String situation, @Path("state") String state, @Path("note") String note, @Path("location") String location);

}
