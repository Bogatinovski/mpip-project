package mk.ukim.finki.mpip.helloworld.services;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.MoviesActivity;
import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;
import mk.ukim.finki.mpip.helloworld.persistence.MoviesDatabase;
import mk.ukim.finki.mpip.helloworld.utils.NotificationUtils;
import mk.ukim.finki.mpip.helloworld.web.MoviesApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dejan on 17.12.2017.
 */

public class DownloadAndSaveMoviesService extends IntentService {


    public static final String MOVIES_DATABASE_UPDATED = "mk.ukim.finki.mpip.helloworld.MOVIES_DATABASE_UPDATED";

    private MoviesApi webApi;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownloadAndSaveMoviesService() {
        super("Download and Save movies");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("DownloadAndSaveMovies", "OnCreate");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webApi = retrofit.create(MoviesApi.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationUtils.makeNotification(this,
                "my_channel_05",
                "Downloading",
                "Downloading Movies from API",
                R.drawable.picture,
                new Intent(this, MoviesActivity.class),
                1234,
                b-> b.setAutoCancel(true));


        String query = intent.getStringExtra("query");
        Log.d("DownloadAndSaveMovies", "Searching WebApi: " + query);

        Call<MoviesSearchResult> movies = webApi.search(query);

        try {
            MoviesSearchResult result = movies.execute().body();
            saveResultsInDb(result.Search);
            this.sendBroadcast(new Intent(MOVIES_DATABASE_UPDATED));

            NotificationUtils.makeNotification(this,
                    "my_channel_05",
                    "Downloaded",
                    "Downloading Movies from API",
                    R.drawable.picture,
                    new Intent(this, MoviesActivity.class),
                    1234,
                    b-> b.setAutoCancel(true));
            //NotificationUtils.cancel(this, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResultsInDb(List<MovieSearchEntry> result) {
        Log.d("DownloadAndSaveMovies", "Saving to local db");

        MoviesDatabase db = Room.databaseBuilder(this,
                MoviesDatabase.class,
                "movies"
        ).build();


        db.movieDao().deleteAll();
        db.movieDao()
                .insertAll(result.toArray(new MovieSearchEntry[]{}));


    }
}
