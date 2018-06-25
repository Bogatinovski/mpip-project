package mk.ukim.finki.mpip.helloworld.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mk.ukim.finki.mpip.helloworld.model.Country;
import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;

/**
 * Created by Dejan on 17.12.2017.
 */

@Database(entities = {MovieSearchEntry.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}