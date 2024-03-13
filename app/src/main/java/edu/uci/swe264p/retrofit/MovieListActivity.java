package edu.uci.swe264p.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;

public class MovieListActivity extends AppCompatActivity {
    static final String TAG = MovieListActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Movie> movieslist = new ArrayList<>();
    static String BASE_URL = "";
    static String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        BASE_URL = extras.getString("BASE_URL");
        API_KEY = extras.getString("API_KEY");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.rvMovieList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieslist.add(new Movie(1f, "hi","hi","hi","hi"));
        MovieListAdapter mdapter = new MovieListAdapter(movieslist);
        recyclerView.setAdapter(mdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApiService movieApi = retrofit.create(MovieApiService.class);

        Call<JsonObject> call = movieApi.getTopRatedMovies(API_KEY);
        // asynchronous call
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                List<Movie> movies = new ArrayList<>();
                JsonArray  result = (JsonArray) jsonObject.get("results");
                for (JsonElement jsonElement : result) {
                    // Convert each element to a JsonObject
                    JsonObject jsonObj = jsonElement.getAsJsonObject();
                    String title = jsonObj.get("title").getAsString();
                    String release_date = jsonObj.get("release_date").getAsString();
                    Float vote_average = jsonObj.get("vote_average").getAsFloat();
                    String overview = jsonObj.get("overview").getAsString();
                    String poster_path = jsonObj.get("poster_path").getAsString();
                    movies.add(new Movie(vote_average, poster_path, title, release_date, overview));
                }
                mdapter.setMovieList(movies);

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Log.e("API Error", "hello");
                Log.e(TAG, throwable.toString());
            }
        });

    }






    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
    }*/
}
