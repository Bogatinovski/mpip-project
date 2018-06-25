package mk.ukim.finki.mpip.helloworld.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mk.ukim.finki.mpip.helloworld.model.Country;

/**
 * Created by ristes on 15.11.17.
 */
@Database(entities = {Country.class}, version = 1)
public abstract class CountriesDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}
