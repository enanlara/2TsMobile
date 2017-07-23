package br.com.a2ts_mobile.Synchronization_Manager;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Enan on 6/17/2017.
 */

public interface SynchronizationThingsService {

    @POST("/editThings")
    @FormUrlEncoded
    public Call<String> editThings(@Field("cod_bem") String codeThings, @Field("descricao") String description, @Field("estado") String state, @Field("localizacao") String location, @Field("situacao") String situation);

}
