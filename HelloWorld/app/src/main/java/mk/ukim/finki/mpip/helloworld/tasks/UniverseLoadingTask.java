package mk.ukim.finki.mpip.helloworld.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import mk.ukim.finki.mpip.helloworld.model.Universe;
import mk.ukim.finki.mpip.helloworld.web.MoviesApi;
import mk.ukim.finki.mpip.helloworld.web.UniverseApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static mk.ukim.finki.mpip.helloworld.utils.ConnectivityUtils.TYPE_NOT_CONNECTED;
import static mk.ukim.finki.mpip.helloworld.utils.ConnectivityUtils.getConnectivityStatus;

/**
 * Created by Dejan on 25.06.2018.
 */

public class UniverseLoadingTask extends AsyncTask<Void, Void, Universe> {

    private UniverseApi service;
    private IUniverseCallbacks callbacks;
    private Context context;

    public UniverseLoadingTask(IUniverseCallbacks callbacks){
        this.callbacks = callbacks;
        this.context = (Context)callbacks;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-185-114-159.eu-central-1.compute.amazonaws.com:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(UniverseApi.class);
    }

    @Override
    protected Universe doInBackground(Void... voids) {
        Universe result = null;
        result = loadDataFromApi();

        return  result;
    }

    @Override
    protected void onPostExecute(Universe universe) {
        super.onPostExecute(universe);
        callbacks.setUniverseData(universe);
    }

    private Universe loadDataFromApi()
    {
        Universe result = null;
        Call<Universe> data = service.getHomePage();

        try {
            result = data.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
