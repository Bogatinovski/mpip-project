package mk.ukim.finki.mpip.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.adapters.CountryAdapter;
import mk.ukim.finki.mpip.helloworld.loaders.CountriesListLoader;
import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.services.DownloadAndSaveCountriesService;
import mk.ukim.finki.mpip.helloworld.tasks.CountryLoadingTask;
import mk.ukim.finki.mpip.helloworld.web.CountriesApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static mk.ukim.finki.mpip.helloworld.services.DownloadAndSaveCountriesService.DATABASE_UPDATED;
import static mk.ukim.finki.mpip.helloworld.utils.ConnectivityUtils.TYPE_NOT_CONNECTED;
import static mk.ukim.finki.mpip.helloworld.utils.ConnectivityUtils.getConnectivityStatus;

public class CountriesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Country>> {

    public static final String WEB_LOADING_PREFS = "web_loading";
    public static final String IS_DATA_LOADED = "data_loaded";

    private RecyclerView recyclerView;
    @Deprecated
    private CountryLoadingTask task;
    private CountryAdapter adapter;
    private UpdateCountriesReceiver receiver;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.receiver = new UpdateCountriesReceiver();
        initUI();
        initRecyclerView();
        shouldLoadInService();
        loadDataFromDb();
    }


    @Override
    protected void onStart() {
        super.onStart();
        this.started = true;
        IntentFilter filter = new IntentFilter(DATABASE_UPDATED);
        this.registerReceiver(receiver, filter);
        invokeDataLoadingInService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        cancelTask();
        super.onDestroy();
    }

    private class UpdateCountriesReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            CountriesActivity.this
                    .getSupportLoaderManager()
                    .restartLoader(0,
                            null,
                            CountriesActivity.this)
                    .forceLoad();
            ;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            this.startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Country>> onCreateLoader(int id, Bundle args) {
        return new CountriesListLoader(this.getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Country>> loader, List<Country> data) {
        this.adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Country>> loader) {
        this.adapter.setData(new ArrayList<>());
    }


    private void loadDataFromDb() {
        this.getSupportLoaderManager()
                .initLoader(0,
                        null,
                        this)
                .forceLoad();
    }

    private void initUI() {
        setContentView(R.layout.activity_countries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.countries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CountryAdapter(this.getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private boolean shouldLoadInService() {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);

        Boolean dataLoaded = prefs.getBoolean(IS_DATA_LOADED, false);
        if (dataLoaded) {
            return false;
        }

        if (getConnectivityStatus(this) == TYPE_NOT_CONNECTED) {
            Toast.makeText(this,
                    getString(R.string.turn_on_internet),
                    Toast.LENGTH_LONG)
                    .show();
            if (!started) {
                this.startActivity(new Intent(this, SettingsActivity.class));
            }
            return false;
        }
        return true;
    }

    private void invokeDataLoadingInService() {
        if (!shouldLoadInService()) {
            return;
        }
        Toast.makeText(this,
                getString(R.string.reloading_countries),
                Toast.LENGTH_LONG)
                .show();
        this.startService(
                new Intent(
                        this,
                        DownloadAndSaveCountriesService.class
                )
        );

    }


    @Deprecated
    private void invokeDataLoading(CountryAdapter countryAdapter) {
        task = new CountryLoadingTask(countryAdapter);
        task.execute();
    }

    @Deprecated
    private void cancelTask() {
        if (task != null) {
            task.cancel(true);
        }
    }


    /**
     * Don't do this
     *
     * @param countryAdapter
     */
    @Deprecated
    private void loadInUiThread(CountryAdapter countryAdapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.countries_api))
                // for converting Json into Objects
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountriesApi service = retrofit.create(CountriesApi.class);

        Call<List<Country>> call = service.allCountries();

        try {
            List<Country> result = call.execute().body();
            countryAdapter.setData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
