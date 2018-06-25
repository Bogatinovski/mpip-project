package mk.ukim.finki.mpip.helloworld.tasks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.SettingsActivity;
import mk.ukim.finki.mpip.helloworld.model.MovieDetails;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;
import mk.ukim.finki.mpip.helloworld.persistence.MoviesDatabase;
import mk.ukim.finki.mpip.helloworld.web.MoviesApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static mk.ukim.finki.mpip.helloworld.utils.ConnectivityUtils.TYPE_NOT_CONNECTED;
import static mk.ukim.finki.mpip.helloworld.utils.ConnectivityUtils.getConnectivityStatus;

/**
 * Created by Dejan on 14.11.2017.
 */

public class MovieDetailsLoadingTask extends AsyncTask<Void, Void, MovieDetails>
{
    private MoviesApi service;
    private String imdbId;
    private IMovieCallbacks callbacks;
    private Context context;

    public MovieDetailsLoadingTask(IMovieCallbacks callbacks, String imdbId) {
        this.imdbId = imdbId;
        this.callbacks = callbacks;
        this.context = (Context)callbacks;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(MoviesApi.class);
    }

    @Override
    protected void onPreExecute() {
        // Common scenario: Start progress dialog
        super.onPreExecute();
    }

    @Override
    protected MovieDetails doInBackground(Void... params) {
        MovieDetails result = null;

        if (getConnectivityStatus(context) == TYPE_NOT_CONNECTED) {
             result = loadDataFromDb();
        }
        else {
            result = loadDataFromApi();
        }

        return  result;
    }

    private MovieDetails loadDataFromApi()
    {
        Log.d("loadDataFromApi", "Loading...");

        MovieDetails result = null;
        Call<MovieDetails> movies = service.getByImdbId(imdbId);

        try {
            result = movies.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("loadDataFromApi", "Loaded: " + result.Title);

        return result;
    }

    private MovieDetails loadDataFromDb() {
        Log.d("loadDataFromDb", "Loading...");

        MoviesDatabase db = Room.databaseBuilder(context,
                MoviesDatabase.class,
                "movies"
        ).allowMainThreadQueries().build();
        Log.d("loadDataFromDb", "ImdbId: " + imdbId);
        List<MovieSearchEntry> allMovies = db.movieDao().getAll();

        for(MovieSearchEntry m: allMovies){
            Log.d("MOVIE", m.Title + ":" + m.imdbID);
        }

        MovieSearchEntry movie = db.movieDao().getByImdbId(imdbId);

        MovieDetails result = new MovieDetails();
        result.Title = movie.Title;
        result.Year = movie.Year;
        result.Poster = movie.Poster;
        result.ImdbID = movie.imdbID;

        Log.d("loadDataFromDb", "Loaded: " + movie.Title);

        return result;
    }

    @Override
    protected void onPostExecute(MovieDetails movie) {
        super.onPostExecute(movie);
        callbacks.setMovieDetails(movie);
    }
}
