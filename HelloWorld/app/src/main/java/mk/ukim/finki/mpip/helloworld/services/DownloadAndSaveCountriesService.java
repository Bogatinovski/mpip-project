package mk.ukim.finki.mpip.helloworld.services;

import android.app.IntentService;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.CountriesActivity;
import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.persistence.CountriesDatabase;
import mk.ukim.finki.mpip.helloworld.utils.NotificationUtils;
import mk.ukim.finki.mpip.helloworld.web.CountriesApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DownloadAndSaveCountriesService extends IntentService {


    public static final String DATABASE_UPDATED = "mk.ukim.finki.mpip.helloworld.DATABASE_UPDATED";
    private CountriesApi webApi;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownloadAndSaveCountriesService() {
        super("Download and Save countries");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.getString(R.string.countries_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webApi = retrofit.create(CountriesApi.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationUtils.makeNotification(this,
                "my_channel_05",
                "Downloading",
                "Downloading Countries from API",
                R.drawable.picture,
                new Intent(this, CountriesActivity.class),
                1234,
                b-> b.setAutoCancel(true));

        Call<List<Country>> countries = this.webApi.allCountries();
        try {
            List<Country> result = countries.execute().body();

            saveResultsInDb(result);

            setDataLoadedPref();

            this.sendBroadcast(new Intent(DATABASE_UPDATED));

            NotificationUtils.makeNotification(this,
                    "my_channel_05",
                    "Downloaded",
                    "Downloading Countries from API",
                    R.drawable.picture,
                    new Intent(this, CountriesActivity.class),
                    1234,
                    b-> b.setAutoCancel(true));
            //NotificationUtils.cancel(this, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResultsInDb(List<Country> result) {
        CountriesDatabase db = Room.databaseBuilder(this,
                CountriesDatabase.class,
                "countries"
        ).build();


        db.countryDao().deleteAll();
        db.countryDao()
                .insertAll(result.toArray(new Country[]{}));


    }

    private void setDataLoadedPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(CountriesActivity.IS_DATA_LOADED, true);

        editor.commit();


    }
}
