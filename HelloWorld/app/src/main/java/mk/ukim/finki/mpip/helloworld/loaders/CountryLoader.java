package mk.ukim.finki.mpip.helloworld.loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.persistence.CountriesDatabase;

/**
 * Created by ristes on 15.11.17.
 */

public class CountryLoader extends AsyncTaskLoader<Country> {

    private String countryName;

    public CountryLoader(Context context, String countryName) {
        super(context);
        this.countryName = countryName;
    }

    @Override
    public Country loadInBackground() {
        CountriesDatabase db = Room.databaseBuilder(getContext(),
                CountriesDatabase.class, "countries").build();
        return db.countryDao().getByName(countryName);
    }

}
