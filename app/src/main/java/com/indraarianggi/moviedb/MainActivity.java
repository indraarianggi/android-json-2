package com.indraarianggi.moviedb;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.indraarianggi.moviedb.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/search/movie?api_key=fec5e78abb7b6bf423345d49df55aa74";

    final String PARAM_QUERY = "query";

    EditText edtSearchMovie;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearchMovie = (EditText)findViewById(R.id.edt_search_movie);
        recyclerView = (RecyclerView)findViewById(R.id.rv_movie_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void searchMovie(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading . . .");
        progressDialog.show();

        // Mendapatkan teks dari input user.
        String searchMovieQuery = edtSearchMovie.getText().toString().trim();

        // Proses build uri dari teks yang didapat dari user.
        Uri uri = Uri.parse(BASE_MOVIE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_QUERY, searchMovieQuery)
                .build();

        String url = uri.toString();

        // Proses request.
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                "Gagal", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();

                final ArrayList<Movie> movieList = new ArrayList<>();

                String jsonData = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject objectMovie = jsonArray.getJSONObject(i);

                        String imgPath  = objectMovie.get("poster_path").toString();
                        String title    = objectMovie.get("title").toString();
                        String release  = objectMovie.get("release_date").toString();

                        Movie movie = new Movie(imgPath, title, release);

                        movieList.add(movie);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), movieList);

                        recyclerView.setAdapter(movieAdapter);
                    }
                });
            }
        });

    }
}