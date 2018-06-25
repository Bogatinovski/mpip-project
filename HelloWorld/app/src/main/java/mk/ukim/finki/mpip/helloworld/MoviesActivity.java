package mk.ukim.finki.mpip.helloworld;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.adapters.MoviesAdapter;
import mk.ukim.finki.mpip.helloworld.loaders.CountriesListLoader;
import mk.ukim.finki.mpip.helloworld.loaders.MoviesListLoader;
import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;
import mk.ukim.finki.mpip.helloworld.persistence.MoviesDatabase;
import mk.ukim.finki.mpip.helloworld.services.DownloadAndSaveCountriesService;
import mk.ukim.finki.mpip.helloworld.services.DownloadAndSaveMoviesService;
import mk.ukim.finki.mpip.helloworld.tasks.MovieLoadingTask;

import static mk.ukim.finki.mpip.helloworld.services.DownloadAndSaveMoviesService.MOVIES_DATABASE_UPDATED;

public class MoviesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieSearchEntry>>, SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private MoviesActivity.UpdateMoviesReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.receiver = new UpdateMoviesReceiver();

        initUI();
        initRecyclerView();
        loadDataFromDb();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(MOVIES_DATABASE_UPDATED);
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) MoviesActivity.this.getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MoviesActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.movies_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        moviesAdapter = new MoviesAdapter(this.getApplicationContext());
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("SEARCH QUERY", query);
        Log.d("MoviesActivity", "Search button clicked");
        invokeDataLoadingInService(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public Loader<List<MovieSearchEntry>> onCreateLoader(int id, Bundle args) {
        Log.d("MoviesActivity", "onCreateLoader");
        return new MoviesListLoader(this.getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<MovieSearchEntry>> loader, List<MovieSearchEntry> data) {
        Log.d("MoviesActivity", "onLoadFinished");
        MoviesSearchResult result = new MoviesSearchResult();
        result.Search = data;

        this.moviesAdapter.setData(result);
    }

    @Override
    public void onLoaderReset(Loader<List<MovieSearchEntry>> loader) {
        Log.d("MoviesActivity", "onLoaderReset");
        this.moviesAdapter.setData(new MoviesSearchResult());
    }

    private void initUI() {
        setContentView(R.layout.activity_movies);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    private void loadDataFromDb() {
        MoviesDatabase db = Room.databaseBuilder(this,
                MoviesDatabase.class,
                "movies"
        ).allowMainThreadQueries().build();

        List<MovieSearchEntry> movies = db.movieDao().getAll();
        MoviesSearchResult result = new MoviesSearchResult();
        result.Search = movies;
        Log.d("loadDataFromDb", movies.size() + "");
        moviesAdapter.setData(result);
    }

    private void invokeDataLoadingInService(String query) {
        Toast.makeText(this,
                getString(R.string.reloading_movies),
                Toast.LENGTH_LONG)
                .show();

        Log.d("MoviesActivity", "Invoking DownloadAndSaveMoviesService");

        Intent startServiceIntent = new Intent(this, DownloadAndSaveMoviesService.class );
        startServiceIntent.putExtra("query", query);
        this.startService(startServiceIntent);
    }

    private class UpdateMoviesReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("UpdateMoviesReceiver", "onReceive");

            MoviesActivity.this
                    .getSupportLoaderManager()
                    .restartLoader(10,
                            null,
                            MoviesActivity.this)
                    .forceLoad();
        }
    }
}
