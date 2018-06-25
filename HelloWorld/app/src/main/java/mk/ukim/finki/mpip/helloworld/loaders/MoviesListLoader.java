package mk.ukim.finki.mpip.helloworld.loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;


import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.persistence.MoviesDatabase;

/**
 * Created by Dejan on 17.12.2017.
 */

public class MoviesListLoader extends AsyncTaskLoader<List<MovieSearchEntry>> {

    public MoviesListLoader(Context context) {
        super(context);
    }

    @Override
    public List<MovieSearchEntry> loadInBackground() {
        // todo: implement this
        MoviesDatabase db = Room.databaseBuilder(getContext(),
                MoviesDatabase.class, "movies").build();

        return db.movieDao().getAll();
    }

}