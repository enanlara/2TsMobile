package br.com.a2ts_mobile.User_Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Enan on 6/17/2017.
 */

public interface UserService {
    @GET("/user_autenticate/email={email}&password={password}")
    public Call<UserModel> logar(@Path("email") String email, @Path("password") String password);

}
