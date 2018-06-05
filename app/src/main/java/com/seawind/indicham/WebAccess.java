package com.seawind.indicham;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 05-Jun-18.
 */

public class WebAccess {
    static final String MAIN_URL = "http://192.168.1.111/php/indichem/";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MAIN_URL).build();

    public static GitHubService getService() {
        return retrofit.create(GitHubService.class);
    }

    public interface GitHubService {
        @GET("api.php?")
        Call<ResponseBody> getAboutus(@Query("apiname") String key);
        @GET("api.php?")
        Call<ResponseBody> UserLogin(@Query("apiname") String key,@Query("user_email") String user_email,@Query("user_password") String user_password);
        @GET("api.php?")
        Call<ResponseBody> UserRegistration(@Query("apiname") String key,@Query("data") String data);
    }

}
