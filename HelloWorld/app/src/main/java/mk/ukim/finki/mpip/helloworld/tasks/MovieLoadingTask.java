package mk.ukim.finki.mpip.helloworld.tasks;

import android.os.AsyncTask;
import android.util.Log;

import mk.ukim.finki.mpip.helloworld.adapters.MoviesAdapter;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;
import mk.ukim.finki.mpip.helloworld.web.MoviesApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dejan on 14.11.2017.
 */

public class MovieLoadingTask  extends AsyncTask<Void, Void, MoviesSearchResult>
{
    private MoviesApi service;
    private MoviesAdapter adapter;
    private String query;

    public MovieLoadingTask(MoviesAdapter adapter, String query) {
        this.query = query;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(MoviesApi.class);
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        // Common scenario: Start progress dialog
        super.onPreExecute();
    }

    @Override
    protected MoviesSearchResult doInBackground(Void... params) {
        Call<MoviesSearchResult> movies = service.search(query);

        try {
            Log.d("MOVIELOADINGTASK", "EXECUTE");
            MoviesSearchResult result = movies.execute().body();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MoviesSearchResult();
    }

    @Override
    protected void onPostExecute(MoviesSearchResult movies) {
        super.onPostExecute(movies);
        Log.d("MOVIELOADINGTASK", "UPDATE ADAPTER");

        adapter.setData(movies);
        adapter.notifyDataSetChanged();

    }
}
