package br.com.a2ts_mobile.User_Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Enan on 6/17/2017.
 */

public interface UserService {
    @POST("/logar")
    @FormUrlEncoded
    public Call<List<UserModel>> logar(@Field("user") String email, @Field("senha") String password);

}
