package mk.ukim.finki.mpip.helloworld.loaders;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.MovieSearchEntry;
import mk.ukim.finki.mpip.helloworld.model.Universe;
import mk.ukim.finki.mpip.helloworld.persistence.MoviesDatabase;

/**
 * Created by Dejan on 17.12.2017.
 */

public class UniverseLoader extends AsyncTaskLoader<Universe> {

    public UniverseLoader(Context context) {
        super(context);
    }

    @Override
    public Universe loadInBackground() {
       return new Universe();
    }

}