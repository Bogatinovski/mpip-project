package mk.ukim.finki.mpip.helloworld.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.adapters.CountryAdapter;
import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.web.CountriesApi;
import mk.ukim.finki.mpip.helloworld.web.MoviesApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v7.widget.RecyclerView.Adapter;

/**
 * Created by ristes on 25.10.17.
 */

public class CountryLoadingTask extends AsyncTask<Void, Void, List<Country>> {

    private CountriesApi service;

    private CountryAdapter adapter;

    public CountryLoadingTask(CountryAdapter adapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(
                        adapter
                                .getContext()
                                .getString(R.string.countries_api)
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(CountriesApi.class);
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        // Common scenario: Start progress dialog
        super.onPreExecute();
    }

    @Override
    protected List<Country> doInBackground(Void... params) {
        Call<List<Country>> countries = service.allCountries();
        try {
            List<Country> result = countries.execute().body();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Country> countries) {
        super.onPostExecute(countries);
        adapter.setData(countries);
        adapter.notifyDataSetChanged();

    }
}
