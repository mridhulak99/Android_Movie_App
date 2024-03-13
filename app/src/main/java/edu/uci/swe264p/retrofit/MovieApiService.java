package edu.uci.swe264p.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.google.gson.JsonObject;
import java.util.List;

public interface MovieApiService {
    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<JsonObject> getTopRatedMovies(@Query("api_key") String apiKey);
}
