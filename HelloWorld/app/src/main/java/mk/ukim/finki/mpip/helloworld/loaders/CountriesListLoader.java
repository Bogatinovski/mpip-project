package mk.ukim.finki.mpip.helloworld.loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.persistence.CountriesDatabase;

/**
 * Created by ristes on 15.11.17.
 */

public class CountriesListLoader extends AsyncTaskLoader<List<Country>> {

    public CountriesListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Country> loadInBackground() {
        // todo: implement this
        CountriesDatabase db = Room.databaseBuilder(getContext(),
                CountriesDatabase.class, "countries").build();
        return db.countryDao().getAll();
    }

}
