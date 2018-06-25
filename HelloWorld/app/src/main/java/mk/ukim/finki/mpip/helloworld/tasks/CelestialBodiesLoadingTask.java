package mk.ukim.finki.mpip.helloworld.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.MainActivity;
import mk.ukim.finki.mpip.helloworld.model.CelestialBodyListItem;
import mk.ukim.finki.mpip.helloworld.model.Universe;
import mk.ukim.finki.mpip.helloworld.web.UniverseApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dejan on 25.06.2018.
 */

public class CelestialBodiesLoadingTask extends AsyncTask<Void, Void, List<CelestialBodyListItem>> {

    private UniverseApi service;
    private ICelestialBodiesCallbacks callbacks;
    private Context context;
    private String bodyType;

    public CelestialBodiesLoadingTask(ICelestialBodiesCallbacks callbacks, String bodyType){
        this.callbacks = callbacks;
        this.context = (Context)callbacks;
        this.bodyType = bodyType;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-185-114-159.eu-central-1.compute.amazonaws.com:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(UniverseApi.class);
    }

    @Override
    protected List<CelestialBodyListItem> doInBackground(Void... voids) {
        List<CelestialBodyListItem> result = null;
        result = loadDataFromApi();

        return  result;
    }

    @Override
    protected void onPreExecute() {
        // Common scenario: Start progress dialog
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<CelestialBodyListItem> items) {
        super.onPostExecute(items);
        callbacks.setListData(items);
    }

    private List<CelestialBodyListItem> loadDataFromApi()
    {
        List<CelestialBodyListItem> result = null;
        Call<List<CelestialBodyListItem>> data = null;

        switch (bodyType)
        {
            case MainActivity.CELESTIAL_BODY_TYPE_SATELLITE:
                data = service.getSatellites();
                break;

            case MainActivity.CELESTIAL_BODY_TYPE_ASTEROID:
                data = service.getAsteroids();
                break;

            case MainActivity.CELESTIAL_BODY_TYPE_CONSTELLATION:
                data = service.getConstellations();
                break;

            case MainActivity.CELESTIAL_BODY_TYPE_GALAXY:
                data = service.getGalaxies();
                break;

            case MainActivity.CELESTIAL_BODY_TYPE_PLANET:
                data = service.getPlanets();
                break;

            case MainActivity.CELESTIAL_BODY_TYPE_STAR:
                data = service.getStars();
                break;
        }

        try {
            result = data.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
